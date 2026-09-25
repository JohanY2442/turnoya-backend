package com.turnoya.turnoyabackend.event;

import com.turnoya.turnoyabackend.entity.NivelUrgencia;
import com.turnoya.turnoyabackend.entity.RolUsuario;
import com.turnoya.turnoyabackend.entity.Usuario;
import com.turnoya.turnoyabackend.exception.NotificationFailedException;
import com.turnoya.turnoyabackend.exception.ResourceNotFoundException;
import com.turnoya.turnoyabackend.repository.UsuarioRepository;
import com.turnoya.turnoyabackend.service.EmailService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas unitarias de {@link TurnoEventListener} sin levantar contexto de
 * Spring: {@code UsuarioRepository} se reemplaza por un proxy dinámico que
 * solo implementa {@code findById}, y {@code EmailService} por una
 * implementación de prueba mínima. Así evitamos depender de un framework de
 * mocks concreto y del datasource real.
 */
class TurnoEventListenerTest {

    private final Usuario pacienteDePrueba = Usuario.builder()
            .id(1L)
            .nombre("Juana Pérez")
            .email("juana@example.com")
            .passwordHash("hash")
            .rol(RolUsuario.PACIENTE)
            .build();

    @Test
    void notificaAlPacienteCuandoElUsuarioExiste() {
        AtomicInteger llamadasEmail = new AtomicInteger();
        UsuarioRepository usuarioRepository = repositorioQueDevuelve(Optional.of(pacienteDePrueba));
        EmailService emailService = emailServiceQue((paciente, evento) -> llamadasEmail.incrementAndGet());

        TurnoEventListener listener = new TurnoEventListener(usuarioRepository, emailService);

        assertDoesNotThrow(() -> listener.onTurnoEvent(eventoDePrueba()));
        assertEquals(1, llamadasEmail.get());
    }

    @Test
    void lanzaResourceNotFoundExceptionSiElUsuarioNoExiste() {
        UsuarioRepository usuarioRepository = repositorioQueDevuelve(Optional.empty());
        EmailService emailService = emailServiceQue((paciente, evento) -> {
            throw new AssertionError("No debería llamarse a EmailService si el usuario no existe");
        });

        TurnoEventListener listener = new TurnoEventListener(usuarioRepository, emailService);

        assertThrows(ResourceNotFoundException.class, () -> listener.onTurnoEvent(eventoDePrueba()));
    }

    @Test
    void noPropagaLaExcepcionSiFallaElEnvioDeCorreo() {
        AtomicBoolean seIntentoEnviar = new AtomicBoolean(false);
        UsuarioRepository usuarioRepository = repositorioQueDevuelve(Optional.of(pacienteDePrueba));
        EmailService emailService = emailServiceQue((paciente, evento) -> {
            seIntentoEnviar.set(true);
            throw new NotificationFailedException("SMTP caído");
        });

        TurnoEventListener listener = new TurnoEventListener(usuarioRepository, emailService);

        // El listener corre en un hilo async: no debe propagar la excepción, solo loguearla.
        assertDoesNotThrow(() -> listener.onTurnoEvent(eventoDePrueba()));
        assertEquals(true, seIntentoEnviar.get());
    }

    private TurnoEvent eventoDePrueba() {
        return new TurnoEvent(
                10L,
                pacienteDePrueba.getId(),
                "000123",
                "Medicina General",
                "Posta San Martín",
                LocalDateTime.now(),
                "PENDIENTE",
                TipoEventoTurno.CREACION
        );
    }

    private EmailService emailServiceQue(BiConsumer<Usuario, TurnoEvent> alNotificarTurno) {
        return new EmailService() {
            @Override
            public void enviarNotificacionTurno(Usuario paciente, TurnoEvent evento) {
                alNotificarTurno.accept(paciente, evento);
            }

            @Override
            public void enviarBienvenida(Usuario usuario) {
            }

            @Override
            public void enviarResultadoTriage(Usuario paciente, NivelUrgencia nivelUrgencia, int posicionEnCola) {
            }
        };
    }

    @SuppressWarnings("unchecked")
    private UsuarioRepository repositorioQueDevuelve(Optional<Usuario> resultado) {
        InvocationHandler handler = (proxy, method, args) -> {
            if ("findById".equals(method.getName())) {
                return resultado;
            }
            throw new UnsupportedOperationException(
                    "Método no soportado en este repositorio de prueba: " + method.getName());
        };
        return (UsuarioRepository) Proxy.newProxyInstance(
                UsuarioRepository.class.getClassLoader(),
                new Class<?>[]{UsuarioRepository.class},
                handler);
    }
}

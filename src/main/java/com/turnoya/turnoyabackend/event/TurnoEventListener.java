package com.turnoya.turnoyabackend.event;

import com.turnoya.turnoyabackend.config.AsyncConfig;
import com.turnoya.turnoyabackend.entity.Usuario;
import com.turnoya.turnoyabackend.exception.NotificationFailedException;
import com.turnoya.turnoyabackend.exception.ResourceNotFoundException;
import com.turnoya.turnoyabackend.repository.UsuarioRepository;
import com.turnoya.turnoyabackend.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Escucha los {@link TurnoEvent} publicados cuando un turno se crea o cambia
 * de estado, y dispara el envío de la notificación por correo en segundo
 * plano (fuera del hilo de la petición HTTP original), usando el executor
 * configurado en {@link AsyncConfig}.
 */
@Component
public class TurnoEventListener {

    private static final Logger log = LoggerFactory.getLogger(TurnoEventListener.class);

    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;

    public TurnoEventListener(UsuarioRepository usuarioRepository, EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
    }

    @Async(AsyncConfig.TASK_EXECUTOR_BEAN)
    @EventListener
    public void onTurnoEvent(TurnoEvent evento) {
        log.debug("Procesando evento de turno {} (tipo={}) en el hilo {}",
                evento.getTurnoId(), evento.getTipoEvento(), Thread.currentThread().getName());

        Usuario paciente = usuarioRepository.findById(evento.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró el usuario con id " + evento.getUsuarioId() + " para notificar el turno"));

        try {
            emailService.enviarNotificacionTurno(paciente, evento);
        } catch (NotificationFailedException ex) {
            // No relanzamos: esto corre en un hilo async y el flujo principal
            // (creación/actualización del turno) ya respondió al cliente.
            // Solo dejamos constancia en logs para que DevOps/monitoreo lo detecte.
            log.error("Falló el envío de notificación para el turno {}: {}",
                    evento.getTurnoId(), ex.getMessage(), ex);
        }
    }
}

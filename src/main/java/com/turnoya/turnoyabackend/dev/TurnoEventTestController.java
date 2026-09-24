package com.turnoya.turnoyabackend.dev;

import com.turnoya.turnoyabackend.event.TipoEventoTurno;
import com.turnoya.turnoyabackend.event.TurnoEvent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

/**
 * Endpoint TEMPORAL solo para pruebas manuales del módulo de eventos/correo
 * mientras el servicio real de Turnos (creado por el resto del equipo) todavía
 * no existe.
 * <p>
 * Sirve para comprobar de punta a punta: publicar {@link TurnoEvent} -&gt;
 * {@code TurnoEventListener} (async) -&gt; {@code EmailService} -&gt; correo +
 * registro en {@code Notificacion}.
 * <p>
 * <b>Cuando el servicio real de Turnos exista, este controlador debe
 * eliminarse</b> y la publicación del evento debe hacerse desde allí
 * (por ejemplo, en {@code TurnoService.crearTurno(...)}), tal como se explica
 * en el Javadoc de {@link TurnoEvent}.
 */
@RestController
@RequestMapping("/api/dev/turno-events")
public class TurnoEventTestController {

    private final ApplicationEventPublisher eventPublisher;

    public TurnoEventTestController(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    public ResponseEntity<Void> publicarEventoDePrueba(@Valid @RequestBody TurnoEventTestRequest request) {
        TurnoEvent evento = new TurnoEvent(
                request.turnoId(),
                request.usuarioId(),
                request.numeroTurno(),
                request.especialidad(),
                request.establecimiento(),
                request.fechaHora() != null ? request.fechaHora() : LocalDateTime.now(),
                request.estado(),
                request.tipoEvento()
        );
        eventPublisher.publishEvent(evento);
        return ResponseEntity.accepted().build();
    }

    public record TurnoEventTestRequest(
            @NotNull Long turnoId,
            @NotNull Long usuarioId,
            @NotBlank String numeroTurno,
            @NotBlank String especialidad,
            @NotBlank String establecimiento,
            LocalDateTime fechaHora,
            @NotBlank String estado,
            @NotNull TipoEventoTurno tipoEvento
    ) {
    }
}

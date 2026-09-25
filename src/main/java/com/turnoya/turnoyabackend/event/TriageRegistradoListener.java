package com.turnoya.turnoyabackend.event;

import com.turnoya.turnoyabackend.config.AsyncConfig;
import com.turnoya.turnoyabackend.entity.EstadoTurno;
import com.turnoya.turnoyabackend.entity.NivelUrgencia;
import com.turnoya.turnoyabackend.entity.Turno;
import com.turnoya.turnoyabackend.exception.NotificationFailedException;
import com.turnoya.turnoyabackend.repository.TurnoRepository;
import com.turnoya.turnoyabackend.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

/**
 * Cuando entra un triage nuevo, recalcula la cola (ordenada por prioridad) y
 * le avisa al paciente su nivel de urgencia y su posición.
 */
@Component
public class TriageRegistradoListener {

    private static final Logger log = LoggerFactory.getLogger(TriageRegistradoListener.class);

    private final TurnoRepository turnoRepository;
    private final EmailService emailService;

    public TriageRegistradoListener(TurnoRepository turnoRepository, EmailService emailService) {
        this.turnoRepository = turnoRepository;
        this.emailService = emailService;
    }

    @Async(AsyncConfig.TASK_EXECUTOR_BEAN)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void onTriageRegistrado(TriageRegistradoEvent evento) {
        List<Turno> cola = turnoRepository.buscarCola(
                evento.getCentroId(), evento.getEspecialidadId(), EstadoTurno.EN_ESPERA);

        if (evento.getNivelUrgencia().getPrioridad() >= NivelUrgencia.ALTO.getPrioridad()) {
            log.info("Triage {} en el turno {}: la cola se reordenó ({} pacientes en espera)",
                    evento.getNivelUrgencia(), evento.getTurnoId(), cola.size());
        }

        for (int i = 0; i < cola.size(); i++) {
            Turno turno = cola.get(i);
            if (turno.getId().equals(evento.getTurnoId())) {
                notificarPaciente(turno, evento.getNivelUrgencia(), i + 1);
            }
        }
    }

    private void notificarPaciente(Turno turno, NivelUrgencia nivelUrgencia, int posicion) {
        try {
            emailService.enviarResultadoTriage(turno.getPaciente().getUsuario(), nivelUrgencia, posicion);
        } catch (NotificationFailedException ex) {
            log.error("No se pudo notificar el triage del turno {}: {}", turno.getId(), ex.getMessage());
        }
    }
}

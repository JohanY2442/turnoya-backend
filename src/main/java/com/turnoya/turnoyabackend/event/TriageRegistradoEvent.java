package com.turnoya.turnoyabackend.event;

import com.turnoya.turnoyabackend.entity.NivelUrgencia;
import org.springframework.context.ApplicationEvent;

/**
 * Se publica cuando un paciente registra su triage y su turno entra a la cola.
 */
public class TriageRegistradoEvent extends ApplicationEvent {

    private final Long turnoId;
    private final Long centroId;
    private final Long especialidadId;
    private final NivelUrgencia nivelUrgencia;

    public TriageRegistradoEvent(Object source, Long turnoId, Long centroId, Long especialidadId,
                                 NivelUrgencia nivelUrgencia) {
        super(source);
        this.turnoId = turnoId;
        this.centroId = centroId;
        this.especialidadId = especialidadId;
        this.nivelUrgencia = nivelUrgencia;
    }

    public Long getTurnoId() {
        return turnoId;
    }

    public Long getCentroId() {
        return centroId;
    }

    public Long getEspecialidadId() {
        return especialidadId;
    }

    public NivelUrgencia getNivelUrgencia() {
        return nivelUrgencia;
    }
}

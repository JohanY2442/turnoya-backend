package com.turnoya.turnoyabackend.dto.turno;

import com.turnoya.turnoyabackend.entity.EstadoTurno;
import com.turnoya.turnoyabackend.entity.NivelUrgencia;

import java.time.LocalDateTime;

public record TurnoResponse(
        Long id,
        Long pacienteId,
        String paciente,
        String medico,
        Long centroId,
        String centro,
        Long especialidadId,
        String especialidad,
        LocalDateTime fechaHora,
        EstadoTurno estado,
        Integer prioridad,
        NivelUrgencia nivelUrgencia
) {
}

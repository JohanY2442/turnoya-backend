package com.turnoya.turnoyabackend.dto.turno;

import com.turnoya.turnoyabackend.entity.NivelUrgencia;

import java.time.LocalDateTime;

public record ColaItemResponse(
        int posicion,
        Long turnoId,
        String paciente,
        NivelUrgencia nivelUrgencia,
        LocalDateTime fechaHora
) {
}

package com.turnoya.turnoyabackend.dto.triage;

import com.turnoya.turnoyabackend.entity.NivelUrgencia;

import java.time.LocalDateTime;
import java.util.List;

public record TriageResponse(
        Long id,
        Long turnoId,
        List<String> sintomas,
        NivelUrgencia nivelUrgencia,
        LocalDateTime fechaRegistro
) {
}

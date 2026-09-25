package com.turnoya.turnoyabackend.mapper;

import com.turnoya.turnoyabackend.dto.triage.TriageResponse;
import com.turnoya.turnoyabackend.entity.Triage;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TriageMapper {

    public TriageResponse toResponse(Triage triage) {
        return new TriageResponse(
                triage.getId(),
                triage.getTurno().getId(),
                Arrays.asList(triage.getSintomas().split(",")),
                triage.getNivelUrgencia(),
                triage.getFechaRegistro()
        );
    }
}

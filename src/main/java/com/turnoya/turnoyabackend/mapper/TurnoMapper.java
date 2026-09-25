package com.turnoya.turnoyabackend.mapper;

import com.turnoya.turnoyabackend.dto.turno.ColaItemResponse;
import com.turnoya.turnoyabackend.dto.turno.TurnoResponse;
import com.turnoya.turnoyabackend.entity.NivelUrgencia;
import com.turnoya.turnoyabackend.entity.Turno;
import org.springframework.stereotype.Component;

@Component
public class TurnoMapper {

    public TurnoResponse toResponse(Turno turno) {
        String medico = turno.getMedico() != null ? turno.getMedico().getUsuario().getNombre() : null;
        return new TurnoResponse(
                turno.getId(),
                turno.getPaciente().getId(),
                turno.getPaciente().getUsuario().getNombre(),
                medico,
                turno.getCentroSalud().getId(),
                turno.getCentroSalud().getNombre(),
                turno.getEspecialidad().getId(),
                turno.getEspecialidad().getNombre(),
                turno.getFechaHora(),
                turno.getEstado(),
                turno.getPrioridad(),
                nivelUrgencia(turno)
        );
    }

    public ColaItemResponse toColaItem(Turno turno, int posicion) {
        return new ColaItemResponse(
                posicion,
                turno.getId(),
                turno.getPaciente().getUsuario().getNombre(),
                nivelUrgencia(turno),
                turno.getFechaHora()
        );
    }

    private NivelUrgencia nivelUrgencia(Turno turno) {
        return turno.getTriage() != null ? turno.getTriage().getNivelUrgencia() : null;
    }
}

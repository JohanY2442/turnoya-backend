package com.turnoya.turnoyabackend.dto.turno;

import com.turnoya.turnoyabackend.entity.EstadoTurno;
import jakarta.validation.constraints.NotNull;

public record TurnoEstadoUpdateRequest(@NotNull EstadoTurno estado) {
}

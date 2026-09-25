package com.turnoya.turnoyabackend.dto.turno;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TurnoCreateRequest(
        @NotNull Long centroId,
        @NotNull Long especialidadId,
        @NotNull @FutureOrPresent LocalDateTime fechaHora
) {
}

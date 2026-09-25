package com.turnoya.turnoyabackend.dto.centro;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CentroSaludRequest(
        @NotBlank @Size(max = 150) String nombre,
        @NotBlank @Size(max = 255) String direccion,
        @NotNull @DecimalMin("-90.0") @DecimalMax("90.0") Double latitud,
        @NotNull @DecimalMin("-180.0") @DecimalMax("180.0") Double longitud,
        @NotNull @Min(1) @Max(2000) Integer capacidadDiaria,
        Set<Long> especialidadIds
) {
}

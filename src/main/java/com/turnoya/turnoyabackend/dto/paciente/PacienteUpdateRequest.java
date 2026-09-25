package com.turnoya.turnoyabackend.dto.paciente;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PacienteUpdateRequest(
        @Size(min = 2, max = 100) String nombre,
        @Pattern(regexp = "^9\\d{8}$", message = "debe ser un celular peruano de 9 dígitos que empiece con 9")
        String telefono
) {
}

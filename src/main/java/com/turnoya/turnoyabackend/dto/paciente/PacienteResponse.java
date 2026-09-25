package com.turnoya.turnoyabackend.dto.paciente;

import java.time.LocalDate;

public record PacienteResponse(
        Long id,
        Long usuarioId,
        String nombre,
        String email,
        String telefono,
        String dni,
        LocalDate fechaNacimiento
) {
}

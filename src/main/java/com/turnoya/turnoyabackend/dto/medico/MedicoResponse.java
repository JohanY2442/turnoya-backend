package com.turnoya.turnoyabackend.dto.medico;

import com.turnoya.turnoyabackend.dto.especialidad.EspecialidadResponse;

import java.util.List;

public record MedicoResponse(
        Long id,
        Long usuarioId,
        String nombre,
        String email,
        String colegiatura,
        List<EspecialidadResponse> especialidades
) {
}

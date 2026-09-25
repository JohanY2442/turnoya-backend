package com.turnoya.turnoyabackend.dto.centro;

import com.turnoya.turnoyabackend.dto.especialidad.EspecialidadResponse;

import java.util.List;

public record CentroSaludResponse(
        Long id,
        String nombre,
        String direccion,
        Double latitud,
        Double longitud,
        Integer capacidadDiaria,
        List<EspecialidadResponse> especialidades
) {
}

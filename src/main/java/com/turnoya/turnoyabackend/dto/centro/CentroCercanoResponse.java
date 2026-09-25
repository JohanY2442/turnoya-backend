package com.turnoya.turnoyabackend.dto.centro;

public record CentroCercanoResponse(
        Long id,
        String nombre,
        String direccion,
        double distanciaKm
) {
}

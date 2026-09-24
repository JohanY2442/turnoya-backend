package com.turnoya.turnoyabackend.exception;

import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando un {@code Establecimiento} (posta/centro de salud) no tiene
 * capacidad disponible para aceptar un nuevo turno en la especialidad
 * solicitada. Responde con HTTP 400 (Bad Request).
 */
public class CentroSinCapacidadException extends ApiException {

    public CentroSinCapacidadException(String mensaje) {
        super(HttpStatus.BAD_REQUEST, mensaje);
    }
}

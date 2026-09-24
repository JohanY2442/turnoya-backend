package com.turnoya.turnoyabackend.exception;

import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando un recurso solicitado (turno, usuario, establecimiento, etc.)
 * no existe en la base de datos. Responde con HTTP 404 (Not Found).
 */
public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(String mensaje) {
        super(HttpStatus.NOT_FOUND, mensaje);
    }
}

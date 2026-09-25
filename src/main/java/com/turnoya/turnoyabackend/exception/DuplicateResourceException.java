package com.turnoya.turnoyabackend.exception;

import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando se intenta crear un recurso que ya existe (por ejemplo, un
 * usuario con un email o DNI ya registrado). Responde con HTTP 409 (Conflict).
 */
public class DuplicateResourceException extends ApiException {

    public DuplicateResourceException(String mensaje) {
        super(HttpStatus.CONFLICT, mensaje);
    }
}

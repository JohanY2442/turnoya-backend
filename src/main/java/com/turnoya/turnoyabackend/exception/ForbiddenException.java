package com.turnoya.turnoyabackend.exception;

import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando el usuario autenticado intenta acceder a un recurso que no
 * le pertenece (por ejemplo, el turno de otro paciente). Responde con HTTP 403.
 */
public class ForbiddenException extends ApiException {

    public ForbiddenException(String mensaje) {
        super(HttpStatus.FORBIDDEN, mensaje);
    }
}

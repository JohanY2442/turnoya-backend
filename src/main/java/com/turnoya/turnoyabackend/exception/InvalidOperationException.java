package com.turnoya.turnoyabackend.exception;

import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando la operación no tiene sentido en el estado actual del
 * recurso (por ejemplo, pedir la posición en cola de un turno sin triage).
 * Responde con HTTP 400.
 */
public class InvalidOperationException extends ApiException {

    public InvalidOperationException(String mensaje) {
        super(HttpStatus.BAD_REQUEST, mensaje);
    }
}

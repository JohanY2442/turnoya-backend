package com.turnoya.turnoyabackend.exception;

import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando el nivel de urgencia calculado o informado en un
 * {@code RegistroTriage} no es válido (por ejemplo, fuera del rango permitido
 * o inconsistente con los síntomas reportados). Responde con HTTP 400 (Bad Request).
 */
public class InvalidTriageLevelException extends ApiException {

    public InvalidTriageLevelException(String mensaje) {
        super(HttpStatus.BAD_REQUEST, mensaje);
    }
}

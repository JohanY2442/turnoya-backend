package com.turnoya.turnoyabackend.exception;

import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando el email/usuario y contraseña enviados en el login no
 * coinciden con ningún usuario registrado. Responde con HTTP 401 (Unauthorized).
 */
public class InvalidCredentialsException extends ApiException {

    public InvalidCredentialsException(String mensaje) {
        super(HttpStatus.UNAUTHORIZED, mensaje);
    }
}

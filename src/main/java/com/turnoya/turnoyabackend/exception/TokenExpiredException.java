package com.turnoya.turnoyabackend.exception;

import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando el token de autenticación (JWT) enviado en la petición ha
 * expirado o ya no es válido. Responde con HTTP 401 (Unauthorized).
 */
public class TokenExpiredException extends ApiException {

    public TokenExpiredException(String mensaje) {
        super(HttpStatus.UNAUTHORIZED, mensaje);
    }
}

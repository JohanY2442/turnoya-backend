package com.turnoya.turnoyabackend.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción base para todos los errores de negocio de la API de TurnoYa.
 * <p>
 * Cada excepción específica del dominio (recurso no encontrado, conflicto de turno,
 * credenciales inválidas, etc.) extiende esta clase indicando el {@link HttpStatus}
 * con el que debe responder el {@code GlobalExceptionHandler}. Así evitamos repetir
 * la lógica de "a qué código HTTP corresponde este error" en cada excepción y en el
 * manejador global.
 * <p>
 * Extiende {@link RuntimeException} (no checked) para no obligar a declarar
 * {@code throws} en cada método de servicio/controlador que la lance.
 */
public abstract class ApiException extends RuntimeException {

    private final HttpStatus status;

    protected ApiException(HttpStatus status, String mensaje) {
        super(mensaje);
        this.status = status;
    }

    protected ApiException(HttpStatus status, String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

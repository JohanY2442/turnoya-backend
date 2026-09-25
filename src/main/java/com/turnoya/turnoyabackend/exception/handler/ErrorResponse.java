package com.turnoya.turnoyabackend.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Formato estándar de error que devuelve la API de TurnoYa.
 * <p>
 * Ejemplo de respuesta:
 * <pre>{@code
 * {
 *   "timestamp": "2026-09-24T10:15:30",
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "No se encontró el turno con id 45",
 *   "path": "/api/v1/turnos/45"
 * }
 * }</pre>
 * Cuando el error viene de una validación ({@code @Valid}), el campo
 * {@code errors} trae el detalle campo -> mensaje; en otro caso se omite.
 */
public class ErrorResponse {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    private ErrorResponse(HttpStatus status, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
    }

    public static ErrorResponse of(HttpStatus status, String message, String path) {
        return new ErrorResponse(status, message, path);
    }

    public static ErrorResponse ofValidacion(HttpStatus status, String message, String path, Map<String, String> errors) {
        ErrorResponse response = new ErrorResponse(status, message, path);
        response.errors = errors;
        return response;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}

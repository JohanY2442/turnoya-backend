package com.turnoya.turnoyabackend.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Formato estándar de error que devuelve la API de TurnoYa ante cualquier
 * excepción de negocio, de validación o inesperada.
 * <p>
 * Ejemplo de respuesta:
 * <pre>{@code
 * {
 *   "timestamp": "2026-09-24T10:15:30",
 *   "status": 404,
 *   "error": "Not Found",
 *   "mensaje": "No se encontró el turno con id 45",
 *   "path": "/api/turnos/45"
 * }
 * }</pre>
 * Cuando el error proviene de una validación de un DTO ({@code @Valid}), el
 * campo {@code errores} se completa con el detalle campo -> mensaje y viaja
 * junto al resto de la información; en cualquier otro caso se omite del JSON.
 */
public class ErrorResponse {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String mensaje;
    private final String path;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errores;

    private ErrorResponse(HttpStatus status, String mensaje, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.mensaje = mensaje;
        this.path = path;
    }

    public static ErrorResponse of(HttpStatus status, String mensaje, String path) {
        return new ErrorResponse(status, mensaje, path);
    }

    public static ErrorResponse ofValidacion(HttpStatus status, String mensaje, String path, Map<String, String> errores) {
        ErrorResponse response = new ErrorResponse(status, mensaje, path);
        response.errores = errores;
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

    public String getMensaje() {
        return mensaje;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getErrores() {
        return errores;
    }
}

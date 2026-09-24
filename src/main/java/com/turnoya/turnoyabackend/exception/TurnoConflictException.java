package com.turnoya.turnoyabackend.exception;

import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando una operación sobre un {@code Turno} entra en conflicto con
 * el estado actual del sistema (por ejemplo, doble reserva para el mismo
 * horario/médico, o intento de modificar un turno ya atendido/cancelado).
 * Responde con HTTP 409 (Conflict).
 */
public class TurnoConflictException extends ApiException {

    public TurnoConflictException(String mensaje) {
        super(HttpStatus.CONFLICT, mensaje);
    }
}

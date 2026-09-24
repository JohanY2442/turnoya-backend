package com.turnoya.turnoyabackend.event;

/**
 * Indica qué disparó el evento de turno: su creación o un cambio posterior
 * de estado (por ejemplo, cuando el triage reordena la cola y el turno pasa
 * a ser el siguiente en atenderse).
 */
public enum TipoEventoTurno {
    CREACION,
    CAMBIO_ESTADO
}

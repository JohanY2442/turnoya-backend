package com.turnoya.turnoyabackend.entity;

public enum NivelUrgencia {
    BAJO(1),
    MEDIO(2),
    ALTO(3),
    CRITICO(4);

    private final int prioridad;

    NivelUrgencia(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public static NivelUrgencia desdePrioridad(int prioridad) {
        if (prioridad >= 4) {
            return CRITICO;
        }
        if (prioridad == 3) {
            return ALTO;
        }
        if (prioridad == 2) {
            return MEDIO;
        }
        return BAJO;
    }
}
package com.turnoya.turnoyabackend.entity;

/**
 * Síntomas que el paciente puede seleccionar en el triage. La gravedad va de 1 (leve) a 4 (crítico).
 */
public enum Sintoma {
    DOLOR_PECHO(4),
    DIFICULTAD_RESPIRAR(4),
    PERDIDA_CONCIENCIA(4),
    SANGRADO_ABUNDANTE(4),
    FIEBRE_ALTA(3),
    DOLOR_INTENSO(3),
    POSIBLE_FRACTURA(3),
    VOMITOS(2),
    DIARREA(2),
    FIEBRE(2),
    MAREOS(2),
    TOS(1),
    DOLOR_CABEZA(1),
    DOLOR_GARGANTA(1),
    MALESTAR_GENERAL(1);

    private final int gravedad;

    Sintoma(int gravedad) {
        this.gravedad = gravedad;
    }

    public int getGravedad() {
        return gravedad;
    }
}
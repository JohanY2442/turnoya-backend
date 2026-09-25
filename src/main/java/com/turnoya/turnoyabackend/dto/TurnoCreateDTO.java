package com.turnoya.turnoyabackend.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TurnoCreateDTO {
    @NotNull(message = "El ID del paciente es obligatorio")
    private Long pacienteId;

    @NotNull(message = "El ID del centro de salud es obligatorio")
    private Long centroSaludId;

    @NotNull(message = "La fecha y hora del turno es obligatoria")
    @Future(message = "La fecha del turno debe ser en el futuro")
    private LocalDateTime fechaHora;

    private String motivoConsulta;
}
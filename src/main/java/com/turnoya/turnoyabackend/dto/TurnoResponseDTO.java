package com.turnoya.turnoyabackend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TurnoResponseDTO {
    private Long id;
    private String pacienteNombreCompleto;
    private String centroSaludNombre;
    private LocalDateTime fechaHora;
    private String estado;
    private String motivoConsulta;
}
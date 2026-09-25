package com.turnoya.turnoyabackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TurnoUpdateDTO {
    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
    private String observaciones;
}
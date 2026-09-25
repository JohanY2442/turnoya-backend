package com.turnoya.turnoyabackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CentroSaludDTO {
    private Long id;

    @NotBlank(message = "El nombre del centro es obligatorio")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @Min(value = 1, message = "La capacidad máxima debe ser al menos 1")
    private Integer capacidadMaxima;
}
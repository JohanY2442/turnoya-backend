package com.turnoya.turnoyabackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PacienteCreateDTO {
    @NotBlank(message = "El DNI/Documento es obligatorio")
    @Pattern(regexp = "^[0-9]{8}$", message = "El DNI debe contener exactamente 8 dígitos")
    private String documentoIdentidad;

    private String telefono;
    private String direccion;
    private Long usuarioId;
}
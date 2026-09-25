package com.turnoya.turnoyabackend.dto;

import lombok.Data;

@Data
public class PacienteResponseDTO {
    private Long id;
    private String documentoIdentidad;
    private String telefono;
    private String direccion;
    private UsuarioResponseDTO usuario;
}
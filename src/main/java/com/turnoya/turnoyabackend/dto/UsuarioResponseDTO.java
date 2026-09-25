package com.turnoya.turnoyabackend.dto;

import com.turnoya.turnoyabackend.entity.RolUsuario;
import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private RolUsuario rol;
    private Boolean activo;
}
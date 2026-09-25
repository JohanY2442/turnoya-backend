package com.turnoya.turnoyabackend.dto.usuario;

import com.turnoya.turnoyabackend.entity.RolUsuario;

public record UsuarioResponse(
        Long id,
        String nombre,
        String email,
        String telefono,
        RolUsuario rol
) {
}

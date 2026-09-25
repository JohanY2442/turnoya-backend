package com.turnoya.turnoyabackend.mapper;

import com.turnoya.turnoyabackend.dto.usuario.UsuarioResponse;
import com.turnoya.turnoyabackend.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getRol()
        );
    }
}

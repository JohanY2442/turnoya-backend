package com.turnoya.turnoyabackend.service;

import com.turnoya.turnoyabackend.dto.UsuarioCreateDTO;
import com.turnoya.turnoyabackend.dto.UsuarioResponseDTO;
import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO registrarUsuario(UsuarioCreateDTO dto);
    UsuarioResponseDTO obtenerPorId(Long id);
    List<UsuarioResponseDTO> obtenerTodos();
}
package com.turnoya.turnoyabackend.service;

import com.turnoya.turnoyabackend.dto.TurnoCreateDTO;
import com.turnoya.turnoyabackend.dto.TurnoResponseDTO;
import com.turnoya.turnoyabackend.dto.TurnoUpdateDTO;

import java.util.List;

public interface TurnoService {
    TurnoResponseDTO crearTurno(TurnoCreateDTO dto);
    TurnoResponseDTO obtenerPorId(Long id);
    List<TurnoResponseDTO> obtenerTodos();
    TurnoResponseDTO actualizarEstado(Long id, TurnoUpdateDTO dto);
    void cancelarTurno(Long id);
}
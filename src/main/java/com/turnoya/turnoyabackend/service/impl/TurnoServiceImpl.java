package com.turnoya.turnoyabackend.service.impl;

import com.turnoya.turnoyabackend.dto.TurnoCreateDTO;
import com.turnoya.turnoyabackend.dto.TurnoResponseDTO;
import com.turnoya.turnoyabackend.dto.TurnoUpdateDTO;
import com.turnoya.turnoyabackend.exception.ResourceNotFoundException;
import com.turnoya.turnoyabackend.repository.TurnoRepository;
import com.turnoya.turnoyabackend.service.TurnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnoServiceImpl implements TurnoService {

    private final TurnoRepository turnoRepository;

    @Override
    public TurnoResponseDTO crearTurno(TurnoCreateDTO dto) {
        return new TurnoResponseDTO();
    }

    @Override
    public TurnoResponseDTO obtenerPorId(Long id) {
        return turnoRepository.findById(id)
                .map(turno -> new TurnoResponseDTO())
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado con ID: " + id));
    }

    @Override
    public List<TurnoResponseDTO> obtenerTodos() {
        return Collections.emptyList();
    }

    @Override
    public TurnoResponseDTO actualizarEstado(Long id, TurnoUpdateDTO dto) {
        return new TurnoResponseDTO();
    }

    @Override
    public void cancelarTurno(Long id) {
        if (!turnoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Turno no encontrado con ID: " + id);
        }
        turnoRepository.deleteById(id);
    }
}
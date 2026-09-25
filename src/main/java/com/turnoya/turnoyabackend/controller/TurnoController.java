package com.turnoya.turnoyabackend.controller;

import com.turnoya.turnoyabackend.dto.TurnoCreateDTO;
import com.turnoya.turnoyabackend.dto.TurnoResponseDTO;
import com.turnoya.turnoyabackend.dto.TurnoUpdateDTO;
import com.turnoya.turnoyabackend.service.TurnoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/turnos")
@RequiredArgsConstructor
public class TurnoController {

    private final TurnoService turnoService;

    @PostMapping
    public ResponseEntity<TurnoResponseDTO> crearTurno(@Valid @RequestBody TurnoCreateDTO dto) {
        TurnoResponseDTO nuevoTurno = turnoService.crearTurno(dto);
        return new ResponseEntity<>(nuevoTurno, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(turnoService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TurnoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(turnoService.obtenerTodos());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TurnoResponseDTO> actualizarEstado(
            @PathVariable Long id,
            @Valid @RequestBody TurnoUpdateDTO dto) {
        return ResponseEntity.ok(turnoService.actualizarEstado(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarTurno(@PathVariable Long id) {
        turnoService.cancelarTurno(id);
        return ResponseEntity.noContent().build();
    }
}



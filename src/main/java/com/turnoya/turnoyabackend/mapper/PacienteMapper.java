package com.turnoya.turnoyabackend.mapper;

import com.turnoya.turnoyabackend.dto.paciente.PacienteResponse;
import com.turnoya.turnoyabackend.entity.Paciente;
import com.turnoya.turnoyabackend.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {

    public PacienteResponse toResponse(Paciente paciente) {
        Usuario usuario = paciente.getUsuario();
        return new PacienteResponse(
                paciente.getId(),
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTelefono(),
                paciente.getDni(),
                paciente.getFechaNacimiento()
        );
    }
}

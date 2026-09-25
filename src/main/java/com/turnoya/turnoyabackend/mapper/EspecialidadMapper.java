package com.turnoya.turnoyabackend.mapper;

import com.turnoya.turnoyabackend.dto.especialidad.EspecialidadRequest;
import com.turnoya.turnoyabackend.dto.especialidad.EspecialidadResponse;
import com.turnoya.turnoyabackend.entity.Especialidad;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Component
public class EspecialidadMapper {

    public Especialidad toEntity(EspecialidadRequest request) {
        return Especialidad.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .build();
    }

    public void updateEntity(Especialidad especialidad, EspecialidadRequest request) {
        especialidad.setNombre(request.nombre());
        especialidad.setDescripcion(request.descripcion());
    }

    public EspecialidadResponse toResponse(Especialidad especialidad) {
        return new EspecialidadResponse(especialidad.getId(), especialidad.getNombre(), especialidad.getDescripcion());
    }

    public List<EspecialidadResponse> toResponseList(Collection<Especialidad> especialidades) {
        return especialidades.stream()
                .sorted(Comparator.comparing(Especialidad::getNombre))
                .map(this::toResponse)
                .toList();
    }
}

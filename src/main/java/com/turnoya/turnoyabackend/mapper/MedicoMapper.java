package com.turnoya.turnoyabackend.mapper;

import com.turnoya.turnoyabackend.dto.medico.MedicoResponse;
import com.turnoya.turnoyabackend.entity.PersonalSalud;
import org.springframework.stereotype.Component;

@Component
public class MedicoMapper {

    private final EspecialidadMapper especialidadMapper;

    public MedicoMapper(EspecialidadMapper especialidadMapper) {
        this.especialidadMapper = especialidadMapper;
    }

    public MedicoResponse toResponse(PersonalSalud medico) {
        return new MedicoResponse(
                medico.getId(),
                medico.getUsuario().getId(),
                medico.getUsuario().getNombre(),
                medico.getUsuario().getEmail(),
                medico.getColegiatura(),
                especialidadMapper.toResponseList(medico.getEspecialidades())
        );
    }
}

package com.turnoya.turnoyabackend.repository;

import com.turnoya.turnoyabackend.entity.PersonalSalud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonalSaludRepository extends JpaRepository<PersonalSalud, Long> {

    Optional<PersonalSalud> findByUsuarioEmail(String email);

    boolean existsByColegiatura(String colegiatura);

    List<PersonalSalud> findByEspecialidadesId(Long especialidadId);
}
package com.turnoya.turnoyabackend.repository;

import com.turnoya.turnoyabackend.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
}
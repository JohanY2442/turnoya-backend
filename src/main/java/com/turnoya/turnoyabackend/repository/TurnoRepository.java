package com.turnoya.turnoyabackend.repository;

import com.turnoya.turnoyabackend.entity.EstadoTurno;
import com.turnoya.turnoyabackend.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Long> {

    List<Turno> findByPacienteIdOrderByFechaHoraDesc(Long pacienteId);

    boolean existsByPacienteIdAndEspecialidadIdAndEstadoIn(Long pacienteId, Long especialidadId,
                                                           Collection<EstadoTurno> estados);

    long countByCentroSaludIdAndFechaHoraBetweenAndEstadoNot(Long centroId, LocalDateTime inicio,
                                                             LocalDateTime fin, EstadoTurno estado);

    @Query("""
            SELECT t FROM Turno t
            JOIN FETCH t.paciente p
            JOIN FETCH p.usuario
            WHERE t.centroSalud.id = :centroId
              AND t.especialidad.id = :especialidadId
              AND t.estado = :estado
            ORDER BY t.prioridad DESC, t.fechaHora ASC
            """)
    List<Turno> buscarCola(@Param("centroId") Long centroId,
                           @Param("especialidadId") Long especialidadId,
                           @Param("estado") EstadoTurno estado);
}
package com.turnoya.turnoyabackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * RegistroTriage de la propuesta: síntomas y nivel de urgencia reportados al llegar.
 */
@Entity
@Table(name = "triages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Triage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "turno_id", nullable = false, unique = true)
    private Turno turno;

    @Column(nullable = false, length = 500)
    private String sintomas;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_urgencia", nullable = false, length = 20)
    private NivelUrgencia nivelUrgencia;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    void alCrear() {
        fechaRegistro = LocalDateTime.now();
    }
}
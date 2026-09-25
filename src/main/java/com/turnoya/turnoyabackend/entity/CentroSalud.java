package com.turnoya.turnoyabackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Establecimiento de la propuesta: posta o centro de salud.
 */
@Entity
@Table(name = "centros_salud")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CentroSalud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private Double latitud;

    @Column(nullable = false)
    private Double longitud;

    @Min(1)
    @Column(name = "capacidad_diaria", nullable = false)
    private Integer capacidadDiaria;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "centro_especialidad",
            joinColumns = @JoinColumn(name = "centro_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidad_id")
    )
    @Builder.Default
    private Set<Especialidad> especialidades = new HashSet<>();
}
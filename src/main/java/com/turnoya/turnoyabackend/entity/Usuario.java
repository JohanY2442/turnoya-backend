package com.turnoya.turnoyabackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad base de usuario (sección 3 de la propuesta: Usuario).
 * <p>
 * NOTA PARA EL EQUIPO: este archivo llegó como una clase vacía; como los
 * módulos de errores/eventos/correo (rama {@code feature/brid-errores})
 * necesitan la relación {@code Notificacion -> Usuario} para compilar, se
 * completó aquí con únicamente los campos que ya están definidos en la
 * propuesta técnica (sección "Estructura de Datos"). Si Backend & Database
 * Architecture ya tiene su propia versión más avanzada (validaciones,
 * relaciones con Paciente/Médico, etc.), esta clase debe reconciliarse/
 * reemplazarse con esa antes de mergear a {@code develop}; no se agregó
 * ningún campo fuera de los ya acordados.
 */
@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolUsuario rol;
}

package com.turnoya.turnoyabackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Registro histórico de una notificación enviada a un usuario (correo, SMS o
 * push). El {@code EmailService} crea una fila aquí cada vez que un envío de
 * correo se completa con éxito.
 */
@Entity
@Table(name = "notificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 1000)
    private String mensaje;

    /** Ej: "EMAIL", "SMS", "PUSH". Se deja como texto libre para no acoplarse a un enum aún no acordado por el equipo. */
    @Column(nullable = false)
    private String tipo;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;
}
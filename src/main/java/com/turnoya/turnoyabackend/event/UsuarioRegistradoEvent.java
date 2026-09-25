package com.turnoya.turnoyabackend.event;

import org.springframework.context.ApplicationEvent;

/**
 * Se publica cuando se crea una cuenta nueva (paciente o médico).
 */
public class UsuarioRegistradoEvent extends ApplicationEvent {

    private final Long usuarioId;

    public UsuarioRegistradoEvent(Object source, Long usuarioId) {
        super(source);
        this.usuarioId = usuarioId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }
}

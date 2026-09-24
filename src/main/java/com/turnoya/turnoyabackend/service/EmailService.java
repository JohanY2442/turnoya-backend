package com.turnoya.turnoyabackend.service;

import com.turnoya.turnoyabackend.entity.Usuario;
import com.turnoya.turnoyabackend.event.TurnoEvent;

/**
 * Servicio responsable de notificar por correo electrónico a un paciente
 * sobre su turno (creación o cambio de estado), usando una plantilla HTML, y
 * de registrar el envío exitoso en {@code Notificacion}.
 */
public interface EmailService {

    /**
     * Renderiza la plantilla HTML de notificación de turno, envía el correo
     * al paciente y, si el envío es exitoso, registra una {@code Notificacion}.
     *
     * @param paciente usuario destinatario (ya resuelto desde la base de datos)
     * @param evento   datos del turno que originó la notificación
     * @throws com.turnoya.turnoyabackend.exception.NotificationFailedException si el envío falla
     */
    void enviarNotificacionTurno(Usuario paciente, TurnoEvent evento);
}

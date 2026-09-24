package com.turnoya.turnoyabackend.exception;

import org.springframework.http.HttpStatus;

/**
 * Se lanza cuando el envío de una notificación (correo electrónico, SMS, push)
 * falla por un error del proveedor externo o de configuración. Responde con
 * HTTP 500 (Internal Server Error), ya que no es un error atribuible al cliente.
 */
public class NotificationFailedException extends ApiException {

    public NotificationFailedException(String mensaje) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, mensaje);
    }

    public NotificationFailedException(String mensaje, Throwable causa) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, mensaje, causa);
    }
}

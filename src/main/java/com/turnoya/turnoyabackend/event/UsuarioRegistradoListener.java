package com.turnoya.turnoyabackend.event;

import com.turnoya.turnoyabackend.config.AsyncConfig;
import com.turnoya.turnoyabackend.exception.NotificationFailedException;
import com.turnoya.turnoyabackend.repository.UsuarioRepository;
import com.turnoya.turnoyabackend.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Envía el correo de bienvenida en segundo plano, solo cuando el registro ya
 * se guardó en la base de datos (AFTER_COMMIT).
 */
@Component
public class UsuarioRegistradoListener {

    private static final Logger log = LoggerFactory.getLogger(UsuarioRegistradoListener.class);

    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;

    public UsuarioRegistradoListener(UsuarioRepository usuarioRepository, EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
    }

    @Async(AsyncConfig.TASK_EXECUTOR_BEAN)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void onUsuarioRegistrado(UsuarioRegistradoEvent evento) {
        usuarioRepository.findById(evento.getUsuarioId()).ifPresent(usuario -> {
            try {
                emailService.enviarBienvenida(usuario);
            } catch (NotificationFailedException ex) {
                log.error("No se pudo enviar el correo de bienvenida a {}: {}", usuario.getEmail(), ex.getMessage());
            }
        });
    }
}

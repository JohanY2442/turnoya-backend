package com.turnoya.turnoyabackend.service.impl;

import com.turnoya.turnoyabackend.entity.Notificacion;
import com.turnoya.turnoyabackend.entity.Usuario;
import com.turnoya.turnoyabackend.event.TurnoEvent;
import com.turnoya.turnoyabackend.exception.NotificationFailedException;
import com.turnoya.turnoyabackend.repository.NotificacionRepository;
import com.turnoya.turnoyabackend.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Implementación de {@link EmailService} basada en {@link JavaMailSender} +
 * Thymeleaf (plantilla HTML en {@code templates/email/turno-notificacion.html}).
 * <p>
 * Cada envío exitoso se registra automáticamente en {@code Notificacion}
 * (tal como pide la tarea), y cualquier falla de envío se traduce en
 * {@link NotificationFailedException} para que, si se invoca desde un
 * contexto síncrono, el {@code GlobalExceptionHandler} pueda devolver un
 * HTTP 500 consistente; si se invoca desde el listener asíncrono, este la
 * captura y solo la registra en logs (ver {@code TurnoEventListener}).
 */
@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    private static final String NOMBRE_PLANTILLA = "email/turno-notificacion";
    private static final String TIPO_NOTIFICACION_EMAIL = "EMAIL";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final NotificacionRepository notificacionRepository;

    @Value("${app.mail.from:no-reply@turnoya.com}")
    private String remitente;

    public EmailServiceImpl(JavaMailSender mailSender,
                             TemplateEngine templateEngine,
                             NotificacionRepository notificacionRepository) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.notificacionRepository = notificacionRepository;
    }

    @Override
    public void enviarNotificacionTurno(Usuario paciente, TurnoEvent evento) {
        String asunto = "TurnoYa - Turno N° " + evento.getNumeroTurno();
        String contenidoHtml = renderizarPlantilla(paciente, evento);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setTo(paciente.getEmail());
            helper.setFrom(remitente);
            helper.setSubject(asunto);
            helper.setText(contenidoHtml, true);

            mailSender.send(mimeMessage);
            log.info("Correo de notificación de turno enviado a {}", paciente.getEmail());

            registrarNotificacionExitosa(paciente, asunto);
        } catch (MessagingException | MailException ex) {
            throw new NotificationFailedException(
                    "No se pudo enviar el correo de notificación de turno al paciente " + paciente.getEmail(), ex);
        }
    }

    private String renderizarPlantilla(Usuario paciente, TurnoEvent evento) {
        Context context = new Context();
        context.setVariable("nombrePaciente", paciente.getNombre());
        context.setVariable("numeroTurno", evento.getNumeroTurno());
        context.setVariable("especialidad", evento.getEspecialidad());
        context.setVariable("establecimiento", evento.getEstablecimiento());
        context.setVariable("fechaHora", evento.getFechaHora() != null ? evento.getFechaHora().format(FORMATO_FECHA) : "");
        context.setVariable("estado", evento.getEstado());
        context.setVariable("tipoEvento", evento.getTipoEvento());
        return templateEngine.process(NOMBRE_PLANTILLA, context);
    }

    private void registrarNotificacionExitosa(Usuario paciente, String mensaje) {
        Notificacion notificacion = Notificacion.builder()
                .usuario(paciente)
                .mensaje(mensaje)
                .tipo(TIPO_NOTIFICACION_EMAIL)
                .fechaEnvio(LocalDateTime.now())
                .build();
        notificacionRepository.save(notificacion);
    }
}

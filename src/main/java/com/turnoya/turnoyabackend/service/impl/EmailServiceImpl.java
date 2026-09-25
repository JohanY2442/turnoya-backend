package com.turnoya.turnoyabackend.service.impl;

import com.turnoya.turnoyabackend.entity.NivelUrgencia;
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
 * plantillas Thymeleaf (carpeta {@code templates/email}).
 * <p>
 * Cada envío exitoso se registra en {@code Notificacion} y cualquier falla se
 * traduce en {@link NotificationFailedException}; los listeners asíncronos la
 * capturan y solo la registran en logs.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    private static final String PLANTILLA_TURNO = "email/turno-notificacion";
    private static final String PLANTILLA_BIENVENIDA = "email/bienvenida";
    private static final String PLANTILLA_TRIAGE = "email/triage-registrado";
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
        Context context = new Context();
        context.setVariable("nombrePaciente", paciente.getNombre());
        context.setVariable("numeroTurno", evento.getNumeroTurno());
        context.setVariable("especialidad", evento.getEspecialidad());
        context.setVariable("establecimiento", evento.getEstablecimiento());
        context.setVariable("fechaHora", evento.getFechaHora() != null ? evento.getFechaHora().format(FORMATO_FECHA) : "");
        context.setVariable("estado", evento.getEstado());
        context.setVariable("tipoEvento", evento.getTipoEvento());

        enviar(paciente, "TurnoYa - Turno N° " + evento.getNumeroTurno(), PLANTILLA_TURNO, context);
    }

    @Override
    public void enviarBienvenida(Usuario usuario) {
        Context context = new Context();
        context.setVariable("nombre", usuario.getNombre());
        context.setVariable("rol", usuario.getRol().name());

        enviar(usuario, "Bienvenido a TurnoYa", PLANTILLA_BIENVENIDA, context);
    }

    @Override
    public void enviarResultadoTriage(Usuario paciente, NivelUrgencia nivelUrgencia, int posicionEnCola) {
        Context context = new Context();
        context.setVariable("nombrePaciente", paciente.getNombre());
        context.setVariable("nivelUrgencia", nivelUrgencia.name());
        context.setVariable("posicion", posicionEnCola);

        enviar(paciente, "TurnoYa - Triage registrado", PLANTILLA_TRIAGE, context);
    }

    private void enviar(Usuario destinatario, String asunto, String plantilla, Context context) {
        String contenidoHtml = templateEngine.process(plantilla, context);
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setTo(destinatario.getEmail());
            helper.setFrom(remitente);
            helper.setSubject(asunto);
            helper.setText(contenidoHtml, true);

            mailSender.send(mimeMessage);
            log.info("Correo '{}' enviado a {}", asunto, destinatario.getEmail());

            registrarNotificacion(destinatario, asunto);
        } catch (MessagingException | MailException ex) {
            throw new NotificationFailedException("No se pudo enviar el correo a " + destinatario.getEmail(), ex);
        }
    }

    private void registrarNotificacion(Usuario destinatario, String mensaje) {
        Notificacion notificacion = Notificacion.builder()
                .usuario(destinatario)
                .mensaje(mensaje)
                .tipo(TIPO_NOTIFICACION_EMAIL)
                .fechaEnvio(LocalDateTime.now())
                .build();
        notificacionRepository.save(notificacion);
    }
}

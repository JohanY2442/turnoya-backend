package com.turnoya.turnoyabackend.exception.handler;

import com.turnoya.turnoyabackend.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manejador global de excepciones de la API de TurnoYa.
 * <p>
 * Centraliza la traducción de cualquier excepción lanzada desde los
 * controladores/servicios hacia una respuesta JSON consistente
 * ({@link ErrorResponse}), para que el frontend (web/móvil) siempre reciba el
 * mismo formato sin importar qué módulo generó el error.
 * <p>
 * Orden de manejo:
 * <ol>
 *   <li>{@link ApiException}: errores de negocio propios (404, 409, 400, 401, 500...).</li>
 *   <li>{@link MethodArgumentNotValidException}: fallos de validación de DTOs ({@code @Valid}).</li>
 *   <li>{@link Exception}: cualquier error no previsto, como red de seguridad final.</li>
 * </ol>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Maneja todas las excepciones de negocio propias de TurnoYa
     * (ResourceNotFoundException, TurnoConflictException, InvalidCredentialsException, etc.).
     * Cada excepción ya sabe su propio HttpStatus, por lo que este método es genérico.
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex, HttpServletRequest request) {
        log.warn("{} en {}: {}", ex.getClass().getSimpleName(), request.getRequestURI(), ex.getMessage());
        ErrorResponse body = ErrorResponse.of(ex.getStatus(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    /**
     * Maneja los errores de validación de Bean Validation (anotaciones {@code @NotNull},
     * {@code @NotBlank}, {@code @Email}, etc. sobre los DTOs de entrada marcados con
     * {@code @Valid}). Devuelve, por cada campo que falló, su nombre y el mensaje de
     * validación correspondiente dentro de {@code errores}.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex,
                                                                     HttpServletRequest request) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errores.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        log.warn("Error de validación en {}: {}", request.getRequestURI(), errores);
        ErrorResponse body = ErrorResponse.ofValidacion(
                HttpStatus.BAD_REQUEST,
                "Uno o más campos no cumplen las validaciones requeridas.",
                request.getRequestURI(),
                errores
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * Red de seguridad final: cualquier excepción no controlada explícitamente
     * (NullPointerException, errores de terceros, etc.) se convierte igual en
     * un JSON con el formato estándar, en vez de dejar que Spring devuelva el
     * whitelabel error page por defecto o filtre detalles internos al cliente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception ex, HttpServletRequest request) {
        log.error("Error no controlado en {}", request.getRequestURI(), ex);
        ErrorResponse body = ErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error interno inesperado. Intenta nuevamente más tarde.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}

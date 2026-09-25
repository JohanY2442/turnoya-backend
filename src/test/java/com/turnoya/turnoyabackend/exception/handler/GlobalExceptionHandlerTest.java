package com.turnoya.turnoyabackend.exception.handler;

import com.turnoya.turnoyabackend.exception.ResourceNotFoundException;
import com.turnoya.turnoyabackend.exception.TurnoConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.HandlerMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void devuelve404YFormatoEstandarParaResourceNotFoundException() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/turnos/99");

        ResponseEntity<ErrorResponse> response =
                handler.handleApiException(new ResourceNotFoundException("No se encontró el turno con id 99"), request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals(404, body.getStatus());
        assertEquals("Not Found", body.getError());
        assertEquals("No se encontró el turno con id 99", body.getMessage());
        assertEquals("/api/turnos/99", body.getPath());
        assertNotNull(body.getTimestamp());
        assertNull(body.getErrors());
    }

    @Test
    void devuelve409ParaTurnoConflictException() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/turnos");

        ResponseEntity<ErrorResponse> response =
                handler.handleApiException(new TurnoConflictException("El médico ya tiene un turno en ese horario"), request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(409, response.getBody().getStatus());
    }

    @Test
    void devuelve400ConDetalleDeCamposParaErroresDeValidacion() throws NoSuchMethodException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/pacientes");

        Object target = new Object();
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(target, "pacienteDTO");
        bindingResult.addError(new FieldError("pacienteDTO", "email", "debe ser un email válido"));
        bindingResult.addError(new FieldError("pacienteDTO", "dni", "no debe estar vacío"));

        HandlerMethod handlerMethod = new HandlerMethod(this, "metodoDummy", String.class);
        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(handlerMethod.getMethodParameters()[0], bindingResult);

        ResponseEntity<ErrorResponse> response = handler.handleValidationException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse body = response.getBody();
        assertNotNull(body);
        assertNotNull(body.getErrors());
        assertEquals("debe ser un email válido", body.getErrors().get("email"));
        assertEquals("no debe estar vacío", body.getErrors().get("dni"));
    }

    @Test
    void devuelve500ParaExcepcionesNoControladas() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/reportes");

        ResponseEntity<ErrorResponse> response =
                handler.handleUnexpectedException(new RuntimeException("boom"), request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, response.getBody().getStatus());
    }

    // Método "dummy" público (HandlerMethod requiere reflexión vía Class#getMethod) solo para
    // poder construir un MethodParameter válido en el test de validación.
    @SuppressWarnings("unused")
    public void metodoDummy(String parametro) {
    }
}

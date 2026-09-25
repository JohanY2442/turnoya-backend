package com.turnoya.turnoyabackend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RegisterRequest(
        @NotBlank @Size(max = 100) String nombre,
        @NotBlank @Email @Size(max = 120) String email,
        @NotBlank
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,64}$",
                message = "debe tener entre 8 y 64 caracteres, con al menos una mayúscula, una minúscula y un número")
        String password,
        @Pattern(regexp = "^9\\d{8}$", message = "debe ser un celular peruano de 9 dígitos que empiece con 9")
        String telefono,
        @NotBlank @Pattern(regexp = "^\\d{8}$", message = "debe tener 8 dígitos") String dni,
        @NotNull @Past LocalDate fechaNacimiento
) {
}

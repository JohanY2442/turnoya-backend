package com.turnoya.turnoyabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String rol;

    public AuthResponseDTO(String token, Long id, String email, String rol) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.rol = rol;
    }
}
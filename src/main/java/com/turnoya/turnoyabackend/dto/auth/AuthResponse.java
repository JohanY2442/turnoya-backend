package com.turnoya.turnoyabackend.dto.auth;

import com.turnoya.turnoyabackend.dto.usuario.UsuarioResponse;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresInMs,
        UsuarioResponse usuario
) {
}

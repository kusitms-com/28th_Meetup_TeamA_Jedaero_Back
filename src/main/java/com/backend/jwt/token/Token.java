package com.backend.jwt.token;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Token {
    private final AccessToken accessToken;
    private final RefreshToken refreshToken;

    @Builder
    public Token(AccessToken accessToken, RefreshToken refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
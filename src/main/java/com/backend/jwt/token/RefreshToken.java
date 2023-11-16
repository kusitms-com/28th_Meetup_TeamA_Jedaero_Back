package com.backend.jwt.token;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RefreshToken {
    private final String header;
    private final String data;

    @Builder
    public RefreshToken(String header, String data) {
        this.header = header;
        this.data = data;
    }
}
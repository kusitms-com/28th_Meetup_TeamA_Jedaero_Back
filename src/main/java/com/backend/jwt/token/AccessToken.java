package com.backend.jwt.token;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccessToken {
    private final String header;
    private final String data;

    @Builder
    public AccessToken(String header, String data) {
        this.header = header;
        this.data = data;
    }
}
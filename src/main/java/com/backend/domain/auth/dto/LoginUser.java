package com.backend.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginUser {
    private String email;

    @Builder
    public LoginUser(String email) {
        this.email = email;
    }
}
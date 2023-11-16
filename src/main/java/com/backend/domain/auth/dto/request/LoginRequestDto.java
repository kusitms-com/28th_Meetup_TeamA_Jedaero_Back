package com.backend.domain.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequestDto(@NotEmpty String password, @NotEmpty String email) {
}

package com.backend.domain.mail.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record MailRequest(@NotEmpty String email) {
}
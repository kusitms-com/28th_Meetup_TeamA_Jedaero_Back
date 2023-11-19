package com.backend.domain.mail.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record SmsRequest(@NotEmpty String toNumber) {
}
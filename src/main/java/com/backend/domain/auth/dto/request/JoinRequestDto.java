package com.backend.domain.auth.dto.request;

import com.backend.domain.user.entity.GroupType;
import com.backend.domain.user.entity.User;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.crypto.password.PasswordEncoder;

public record JoinRequestDto(@NotEmpty String password, @NotEmpty String email, @NotEmpty String type,
                             @NotEmpty String typeName, @NotEmpty String proofImageUrl) {
    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(this.password))
                .type(GroupType.create(type))
                .typeName(typeName)
                .proofImageUrl(proofImageUrl)
                .build();
    }
}
package com.backend.domain.auth.dto.request;

import com.backend.domain.university.entity.University;
import com.backend.domain.user.entity.GroupType;
import com.backend.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;

public record JoinRequestDto(@NotBlank String password, @NotBlank String email, @NotBlank String type,
                             @NotBlank String typeName, @NotBlank String proofImageUrl, @NotBlank Long univId) {
    public User toEntity(PasswordEncoder passwordEncoder, University university) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(this.password))
                .type(GroupType.create(type))
                .typeName(typeName)
                .proofImageUrl(proofImageUrl)
                .university(university)
                .build();
    }
}
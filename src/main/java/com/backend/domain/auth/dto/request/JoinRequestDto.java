package com.backend.domain.auth.dto.request;

import com.backend.domain.user.entity.GroupType;
import com.backend.domain.user.entity.User;
import jakarta.validation.constraints.NotEmpty;

public record JoinRequestDto(@NotEmpty String password, @NotEmpty String email, @NotEmpty String type,
                             @NotEmpty String typeName) {
    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .type(GroupType.create(type))
                .typeName(typeName) //todo. type를 enum 타입을 분리할 것
                .build();
    }
}
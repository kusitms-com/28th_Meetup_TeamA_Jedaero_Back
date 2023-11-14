package com.backend.domain.user.entity;

import com.backend.error.ErrorCode;
import com.backend.error.exception.custom.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum GroupType {
    UNIVERSITY("총학생회"),
    COLLEGE("단과대학생회"),
    DEPARTMENT("과학생회");

    private final String typeName;

    @JsonCreator
    public static GroupType create(String request) {
        return Stream.of(values())
                .filter(value -> value.typeName.equalsIgnoreCase(request))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_GROUP_TYPE));
    }
}

package com.backend.domain.university.dto.response;

import com.backend.domain.university.entity.University;

public record getUnivResponseDto(String univName, String email) {
    public static getUnivResponseDto from(University university) {
        return new getUnivResponseDto(university.getName(), university.getEmail());
    }
}
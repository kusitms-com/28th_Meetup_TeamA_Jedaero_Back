package com.backend.domain.university.dto.response;

import com.backend.domain.university.entity.University;

public record getUnivResponseDto(Long universityId, String univName, String email) {
    public static getUnivResponseDto from(University university) {
        return new getUnivResponseDto(university.getUniversityId(), university.getName(), university.getEmail());
    }
}
package com.backend.domain.university.service;

import com.backend.domain.university.dto.response.getUnivResponseDto;
import com.backend.domain.university.entity.University;
import com.backend.domain.university.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UniversityService {
    private final UniversityRepository universityRepository;

    public List<getUnivResponseDto> getAllUniv() {
        List<University> universities = universityRepository.findAll();

        return universities.stream()
                .map(getUnivResponseDto::from)
                .toList();
    }
}
package com.backend.domain.university.controller;

import com.backend.common.dto.ResponseDto;
import com.backend.domain.university.dto.response.getUnivResponseDto;
import com.backend.domain.university.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/univ")
public class UniversityController {
    private final UniversityService universityService;

    @GetMapping
    public ResponseEntity<List<getUnivResponseDto>> getAllUniv() {
        return ResponseDto.ok(universityService.getAllUniv());
    }
}
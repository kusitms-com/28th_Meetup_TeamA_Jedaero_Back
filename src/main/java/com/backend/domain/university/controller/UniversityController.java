package com.backend.domain.university.controller;

import com.backend.common.dto.ResponseDto;
import com.backend.domain.university.dto.response.getUnivResponseDto;
import com.backend.domain.university.service.UniversityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "대학 목록 조회", description = "대학명과 해당 대학 메일 형식을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(array = @ArraySchema(
                                    schema = @Schema(implementation = getUnivResponseDto.class))))
            })
    public ResponseEntity<List<getUnivResponseDto>> getAllUniv() {
        return ResponseDto.ok(universityService.getAllUniv());
    }
}
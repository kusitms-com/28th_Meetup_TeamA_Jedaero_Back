package com.backend.domain.s3.controller;

import com.backend.common.dto.ResponseDto;
import com.backend.domain.auth.dto.Login;
import com.backend.domain.auth.dto.LoginUser;
import com.backend.domain.s3.service.S3Service;
import com.backend.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;

    @Operation(summary = "이미지 추가", description = "s3에 이미지를 추가합니다. 사용자가 업로드한 이미지 파일이 필요합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "이미지 생성 성공, 이미지 파일 이름을 반환합니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping(value = "/create")
    public ResponseEntity<String> uploadImage(@RequestPart MultipartFile file) {
        return ResponseDto.created(s3Service.uploadImage((file)));
    }

    @Operation(summary = "이미지 추가", description = "해당하는 파일 이름의 이미지를 s3에서 삭제합니다. " +
            "사용자의 AccessToken과 지우려는 파일 이름이 필요합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "이미지 삭제 성공 메세지를 반환합니다."),
                    @ApiResponse(responseCode = "401", description = "토큰이 올바르지 않을 때 예외가 발생합니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteImage(@Login LoginUser loginUser, @RequestPart String fileName) {
        s3Service.deleteImage(loginUser, fileName);
        return ResponseDto.ok("이미지 삭제 성공");
    }
}
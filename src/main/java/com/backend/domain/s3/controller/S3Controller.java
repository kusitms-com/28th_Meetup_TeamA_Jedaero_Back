package com.backend.domain.s3.controller;

import com.backend.common.dto.ResponseDto;
import com.backend.domain.auth.dto.Login;
import com.backend.domain.auth.dto.LoginUser;
import com.backend.domain.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping(value = "/create")
    public ResponseEntity<String> uploadImage(@RequestPart MultipartFile file) {
        return ResponseDto.created(s3Service.uploadImage((file)));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteImage(@Login LoginUser loginUser, @RequestPart String fileName) {
        s3Service.deleteImage(loginUser, fileName);
        return ResponseDto.ok("이미지 삭제 성공");
    }
}
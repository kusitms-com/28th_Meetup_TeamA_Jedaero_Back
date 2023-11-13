package com.backend.domain.auth.controller;


import com.backend.common.dto.ResponseDto;
import com.backend.domain.auth.dto.request.JoinRequestDto;
import com.backend.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@Valid @RequestBody JoinRequestDto joinRequestDto) {
        authService.join(joinRequestDto);
        return ResponseDto.noContent();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseDto.noContent();
    }
}
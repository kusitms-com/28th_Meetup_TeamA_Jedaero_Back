package com.backend.domain.auth.controller;

import com.backend.common.dto.ResponseDto;
import com.backend.domain.auth.dto.request.JoinRequestDto;
import com.backend.domain.auth.dto.request.LoginRequestDto;
import com.backend.jwt.token.AccessToken;
import com.backend.jwt.token.RefreshToken;
import com.backend.jwt.token.Token;
import com.backend.domain.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    @Value("${jwt.refresh.expiration}")
    private Integer refreshTokenExpirationPeriod;

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> signIn(@RequestBody @Valid LoginRequestDto loginDto, HttpServletResponse response) {
        Token token = authService.login(loginDto);

        setAccessToken(response, token.getAccessToken());
        setRefreshToken(response, token.getRefreshToken());

        return ResponseDto.ok("로그인 성공");
    }

    @PostMapping("/join")
    public ResponseEntity<String> signUp(@RequestBody @Valid JoinRequestDto joinDto) {
        authService.join(joinDto);

        return ResponseDto.ok("회원가입 성공");
    }

    @PostMapping("/reissue")
    public ResponseEntity<String> reissueToken(@CookieValue(name = "Authorization-refresh") String refreshToken,
                                               HttpServletResponse response) {
        log.info("재발급 토큰 = {}", refreshToken);
        Token token = authService.reissue(
                RefreshToken.builder()
                        .header("Authorization-refresh")
                        .data(refreshToken)
                        .build()
        );

        setAccessToken(response, token.getAccessToken());
        setRefreshToken(response, token.getRefreshToken());

        return ResponseDto.ok("토큰 재발급 성공");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(Authentication authentication, HttpServletResponse response) {
        authService.logout(authentication.getName());
        log.info("이메일: {}", authentication.getName());
        removeCookie(response);

        return ResponseDto.ok("로그아웃 성공");
    }

    private void setAccessToken(HttpServletResponse response, AccessToken accessToken) {
        setHeader(response, accessToken.getHeader(), accessToken.getData());
    }

    private void setRefreshToken(HttpServletResponse response, RefreshToken refreshToken) {
        Cookie cookie = createCookie(refreshToken.getHeader(), refreshToken.getData());
        response.addCookie(cookie);
    }

    private void setHeader(HttpServletResponse response, String header, String data) {
        response.setHeader(header, data);
    }

    private Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(refreshTokenExpirationPeriod);
        cookie.setHttpOnly(true);
        return cookie;
    }

    private void removeCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("Authorization-refresh", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.isHttpOnly();
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}
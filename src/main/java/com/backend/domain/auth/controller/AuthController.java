package com.backend.domain.auth.controller;

import com.backend.common.dto.ResponseDto;
import com.backend.domain.auth.dto.Login;
import com.backend.domain.auth.dto.LoginUser;
import com.backend.domain.auth.dto.request.JoinRequestDto;
import com.backend.domain.auth.dto.request.LoginRequestDto;
import com.backend.error.dto.ErrorResponse;
import com.backend.jwt.token.AccessToken;
import com.backend.jwt.token.RefreshToken;
import com.backend.jwt.token.Token;
import com.backend.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    @Value("${jwt.refresh.expiration}")
    private Integer refreshTokenExpirationPeriod;

    private final AuthService authService;

    @Operation(summary = "로그인", description = "로그인을 합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/login")
    public ResponseEntity<String> signIn(@RequestBody @Valid LoginRequestDto loginDto, HttpServletResponse response) {
        Token token = authService.login(loginDto);

        setAccessToken(response, token.getAccessToken());
        setRefreshToken(response, token.getRefreshToken());

        return ResponseDto.ok("로그인 성공");
    }

    @Operation(summary = "회원가입", description = "회원가입을 합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "회원가입 성공"),
                    @ApiResponse(responseCode = "400", description = "이미 존재하는 이메일입니다."),
                    @ApiResponse(responseCode = "400", description = "잘못된 그룹 종류입니다. " +
                            "type에 총학생회, 단과대학생회, 과학생회만 입력할 수 있습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/join")
    public ResponseEntity<String> signUp(@RequestBody @Valid JoinRequestDto joinDto) {
        authService.join(joinDto);

        return ResponseDto.created("회원가입 성공");
    }

    @Operation(summary = "토큰 재발급", description = "401에러가 발생한 경우 (AccessToken이 만료된 경우) 토큰을 재발급합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "토큰 재발급 성공",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
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

        return ResponseDto.created("토큰 재발급 성공");
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "로그아웃 성공, AccessToken이 필요합니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Login LoginUser loginUser, HttpServletResponse response) {
        authService.logout(loginUser);
        log.info("이메일: {}", loginUser.getEmail());
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
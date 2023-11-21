package com.backend.domain.mail.controller;

import com.backend.domain.mail.dto.request.MailRequest;
import com.backend.domain.mail.dto.request.SmsRequest;
import com.backend.domain.mail.service.MailService;
import com.backend.error.ErrorCode;
import com.backend.error.exception.custom.BusinessException;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MailController {

    private final MailService mailService;

    @Operation(summary = "메일 인증 코드 전송", description = "입력한 메일에 인증 코드를 전송합니다. 형식: test@test.com",
            responses = {
                    @ApiResponse(responseCode = "200", description = "인증 코드 전송 성공, 보낸 인증 코드를 반환합니다.",
                            content = @Content(schema = @Schema(implementation = Integer.class)))
            })
    @GetMapping("/mail")
    public ResponseEntity<Integer> sendCode(@Valid @RequestBody MailRequest emailRequest) {
        return ResponseEntity.ok(mailService.sendAuthenticationCode(emailRequest));
    }

    @Operation(summary = "문자 인증 코드 전송", description = "입력한 번호에 인증 코드를 전송합니다. 형식: 01012345678",
            responses = {
                    @ApiResponse(responseCode = "200", description = "인증 코드 전송 성공, 보낸 인증 코드를 반환합니다.",
                            content = @Content(schema = @Schema(implementation = Integer.class)))
            })
    @RateLimiter(name = "jedero", fallbackMethod = "rateLimiterFallback")
    @GetMapping("/sms")
    public ResponseEntity<Integer> sendSMS(@Valid @RequestBody SmsRequest smsRequest) {
        int result = mailService.sendSMS(smsRequest);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<String> rateLimiterFallback(Throwable t) {
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.set("Retry-After", "10s");
        throw new BusinessException(ErrorCode.TOO_MANY_SMS);
    }
}
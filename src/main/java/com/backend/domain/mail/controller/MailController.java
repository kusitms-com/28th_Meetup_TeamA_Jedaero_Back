package com.backend.domain.mail.controller;

import com.backend.domain.mail.dto.request.MailRequest;
import com.backend.domain.mail.dto.request.SmsRequest;
import com.backend.domain.mail.service.MailService;
import com.backend.error.ErrorCode;
import com.backend.error.exception.custom.BusinessException;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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

    @PostMapping("/mail")
    public ResponseEntity<Integer> sendCode(@Valid @RequestBody MailRequest emailRequest) {
        return ResponseEntity.ok(mailService.sendAuthenticationCode(emailRequest));
    }

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
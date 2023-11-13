package com.backend.domain.mail.controller;

import com.backend.domain.mail.dto.request.MailRequest;
import com.backend.domain.mail.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail")
    public ResponseEntity<Integer> sendCode(@Valid @RequestBody MailRequest emailRequest) {
        return ResponseEntity.ok(mailService.sendAuthenticationCode(emailRequest));
    }
}
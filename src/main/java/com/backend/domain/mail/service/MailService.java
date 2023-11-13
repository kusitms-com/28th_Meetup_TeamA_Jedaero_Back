package com.backend.domain.mail.service;

import com.backend.domain.mail.dto.request.MailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender javaMailSender;
    @Value("{mail.mail.username}")
    private String username;

    public int sendAuthenticationCode(MailRequest mailRequest) {
        int code = (int) (Math.random() * 900000) + 100000;

        SimpleMailMessage message = new SimpleMailMessage();

        log.info("수신자 이메일: {}", mailRequest.email());
        message.setTo(mailRequest.email());
        message.setFrom(username);
        message.setSubject("이메일 인증");
        message.setText("요청하신 인증 번호입니다.\n" + code);

        javaMailSender.send(message);

        return code;
    }
}

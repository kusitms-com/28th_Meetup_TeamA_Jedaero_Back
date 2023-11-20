package com.backend.domain.mail.service;

import com.backend.domain.mail.dto.request.MailRequest;
import com.backend.domain.mail.dto.request.SmsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender javaMailSender;

    private DefaultMessageService messageService;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecret;

    @Value("${coolsms.api.fromNumber}")
    private String fromNumber;


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

    public int sendSMS(SmsRequest smsRequest) {
        Message message = new Message();
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");

        int code = (int) (Math.random() * 9000) + 1000;

        message.setFrom(fromNumber);
        message.setTo(smsRequest.toNumber());
        message.setText("[제대로] 인증번호를 입력해주세요.\n인증번호: " + code);

        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
        log.info("SMS 메세지 결과 = {}", response);

        return code;
    }
}
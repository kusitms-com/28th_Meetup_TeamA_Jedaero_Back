package com.backend.domain.auth.service;

import com.backend.domain.auth.dto.request.JoinRequestDto;
import com.backend.domain.user.entity.User;
import com.backend.domain.user.repository.UserRepository;
import com.backend.error.ErrorCode;
import com.backend.error.exception.custom.BusinessException;
import com.backend.error.exception.custom.TokenException;
import com.backend.jwt.service.JwtService;
import com.backend.util.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RedisUtil redisUtil;

    public void join(JoinRequestDto joinRequestDto) {
        if (userRepository.existsByEmail(joinRequestDto.email())) {
            throw new BusinessException(ErrorCode.ALREADY_EXIST_EMAIL);
        }

        User user = joinRequestDto.toEntity();
        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }

    public void logout(HttpServletRequest request) {
        String accessToken = jwtService.extractAccessToken(request).orElseThrow(() -> new TokenException(ErrorCode.INVALID_TOKEN));
        String email = jwtService.extractUserId(accessToken).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        redisUtil.delete(email);
        redisUtil.setBlackList(email, accessToken, jwtService.getAccessTokenExpirationPeriod());
    }
}
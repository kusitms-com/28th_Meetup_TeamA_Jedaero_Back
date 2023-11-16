package com.backend.domain.auth.service;

import com.backend.domain.auth.dto.request.JoinRequestDto;
import com.backend.domain.auth.dto.request.LoginRequestDto;
import com.backend.jwt.service.JwtProvider;
import com.backend.jwt.token.RefreshToken;
import com.backend.jwt.token.Token;
import com.backend.domain.user.entity.User;
import com.backend.domain.user.repository.UserRepository;
import com.backend.error.ErrorCode;
import com.backend.error.exception.custom.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Token login(LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.email();
        String password = loginRequestDto.password();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        Token token = jwtProvider.createToken(email);

        user.updateRefreshToken(token.getRefreshToken().getData());

        return token;
    }

    @Transactional
    public void join(JoinRequestDto joinRequestDto) {
        if (userRepository.existsByEmail(joinRequestDto.email())) {
            throw new BusinessException(ErrorCode.ALREADY_EXIST_EMAIL);
        }
//        if (userRepository.existsByNickname(joinRequestDto.getNickname())) {}
        userRepository.save(joinRequestDto.toEntity(passwordEncoder));
    }

    @Transactional
    public Token reissue(RefreshToken refreshToken) {
        String refreshTokenValue = refreshToken.getData();

        log.info("리프레쉬 토큰: {}", refreshTokenValue);
        if (jwtProvider.isExpired(refreshTokenValue)) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }

        User user = userRepository.findByRefreshToken(refreshTokenValue)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Token token = jwtProvider.createToken(user.getEmail());

        user.updateRefreshToken(token.getRefreshToken().getData());

        return token;
    }

    @Transactional
    public void logout(String email) {
        log.info("이메일 : {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        user.invalidateRefreshToken();
    }
}
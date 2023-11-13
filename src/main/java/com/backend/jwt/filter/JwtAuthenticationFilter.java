package com.backend.jwt.filter;

import com.backend.domain.user.entity.User;
import com.backend.domain.user.repository.UserRepository;
import com.backend.error.ErrorCode;
import com.backend.error.exception.custom.TokenException;
import com.backend.jwt.service.JwtService;
import com.backend.util.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.security.core.userdetails.User.*;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Set<String> EXCLUDING_URI = new HashSet<>(List.of("/auth/login"));

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (EXCLUDING_URI.stream().anyMatch(url -> url.equals(request.getRequestURI()))) {
            filterChain.doFilter(request, response);
            return;
        }
        log.info("uri = {}, query = {}", request.getRequestURI(), request.getQueryString());
        log.info("JwtAuthenticationProcessingFilter 호출");

        String accessToken = jwtService.extractAccessToken(request).orElse(null);
        if (jwtService.isTokenValid(accessToken)) {
            if (redisUtil.hasKeyBlackList(accessToken)) {
                throw new TokenException(ErrorCode.ALREADY_LOGOUT_MEMBER);
            }
            getAuthentication(accessToken);
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = jwtService.extractRefreshToken(request);
        if (StringUtils.hasText(accessToken) && jwtService.isTokenValid(refreshToken)) {
            log.info("AccessToken 재발급");
            String email = jwtService.extractUserId(refreshToken).orElseThrow(() -> new TokenException(ErrorCode.INVALID_TOKEN));
            String role = jwtService.extractRole(refreshToken).orElseThrow(() -> new TokenException(ErrorCode.INVALID_TOKEN));
            if (isRefreshTokenMatch(email, refreshToken)) {
                reIssueRefreshAndAccessToken(response, refreshToken, email, role);
            }
            filterChain.doFilter(request, response);
            return;
        }

        log.info("유효한 토큰이 없습니다. uri: {}, {}", request.getRequestURI(), accessToken);
        filterChain.doFilter(request, response);
    }

    private void reIssueRefreshAndAccessToken(HttpServletResponse response, String refreshToken, String email, String role) {
        String newAccessToken = jwtService.createAccessToken(email, role);
        String newRefreshToken = jwtService.createRefreshToken(email);
        getAuthentication(newAccessToken);
        redisUtil.delete(email);
        jwtService.updateRefreshToken(email, newRefreshToken);
        jwtService.sendAccessToken(response, newAccessToken);
        jwtService.sendRefreshToken(response, newRefreshToken);
        log.info("AccessToken, RefreshToken 재발급");
    }

    public boolean isRefreshTokenMatch(String email, String refreshToken) {
        log.info("RefreshToken 검증");
        if (redisUtil.get(email).equals(refreshToken)) {
            return true;
        }
        throw new TokenException(ErrorCode.INVALID_TOKEN);
    }

    public void getAuthentication(String accessToken) {
        log.info("인증 처리 메소드 getAuthentication() 호출");
        jwtService.extractUserId(accessToken)
                .flatMap(userRepository::findByEmail)
                .ifPresent(this::saveAuthentication);
    }

    public void saveAuthentication(User user) {
        log.info("인증 허가 메소드 saveAuthentication() 호출");
        String password = user.getPassword();

        UserDetails userDetails = builder()
                .username(user.getEmail())
                .password(password)
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
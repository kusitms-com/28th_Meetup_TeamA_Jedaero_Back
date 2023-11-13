package com.backend.domain.auth.handler;

import com.backend.domain.user.repository.UserRepository;
import com.backend.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String email = extractUsername(authentication);
        String role = extractRole(authentication);
        String accessToken = jwtService.createAccessToken(email, role);
        String refreshToken = jwtService.createRefreshToken(email);
        jwtService.updateRefreshToken(email, refreshToken);

        jwtService.sendAccessToken(response, accessToken);
        jwtService.sendRefreshToken(response, refreshToken);

        //response.setHeader("Role", role);

        log.info("역할 : {}", role);
        log.info("로그인에 성공하였습니다. 아이디 : {}", email);
        log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);
        log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    private String extractRole(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (!authorities.isEmpty()) {
            GrantedAuthority firstAuthority = authorities.iterator().next();
            return firstAuthority.getAuthority();
        }

        //todo. 역할 권한 에러처리
        return null; // 역할이 없을때
    }
}

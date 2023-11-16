package com.backend.jwt.service;

import com.backend.jwt.token.AccessToken;
import com.backend.jwt.token.RefreshToken;
import com.backend.jwt.token.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@Getter
public class JwtProvider {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Integer accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Integer refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ID_CLAIM = "email";
    private static final String ROLE_CLAIM = "roles";
    private static final String BEARER = "Bearer ";

    public Token createToken(String email) {
        AccessToken accessToken = AccessToken.builder()
                .header(accessHeader)
                .data(createAccessToken(email))
                .build();
        RefreshToken refreshToken = RefreshToken.builder()
                .header(refreshHeader)
                .data(createRefreshToken())
                .build();

        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Optional<String> extractToken(HttpServletRequest request) {
        log.info("엑세스 토큰 : {}", request.getHeader("Authorization"));
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(token -> token.startsWith(BEARER))
                .map(token -> token.replace(BEARER, ""));
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get(ID_CLAIM, String.class);
    }

    public boolean isExpired(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public String createAccessToken(String email) {
        Claims claims = Jwts.claims();
        claims.put(ID_CLAIM, email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expireTime(accessTokenExpirationPeriod))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken() {
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expireTime(refreshTokenExpirationPeriod))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private Date expireTime(int expirationPeriod) {
        return new Date(System.currentTimeMillis() + expirationPeriod);
    }
}
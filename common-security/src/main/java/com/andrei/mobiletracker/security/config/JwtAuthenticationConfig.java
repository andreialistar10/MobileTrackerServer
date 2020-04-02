package com.andrei.mobiletracker.security.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@ToString
@Component
public class JwtAuthenticationConfig extends BasicJwtConfig{

    @Value("${user.security.jwt.login-url}")
    private String loginUrl;

    @Value("${user.security.jwt.logout-url}")
    private String logoutUrl;

    @Value("${user.security.jwt.secret-refresh-token}")
    private String secretSignInRefreshToken;

    @Value("${user.security.jwt.expiration-refresh-token}")
    private long expirationRefreshToken;

    @Value("${user.security.jwt.generate-refresh-token-url}")
    private String generateRefreshTokenUrl;
}
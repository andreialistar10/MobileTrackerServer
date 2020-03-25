package com.andrei.mobiletracker.security.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@ToString
@Component
public class JwtAuthenticationConfig extends BasicJwtConfig{

    @Value("${security.jwt.url}")
    private String loginUrl;

    @Value("${security.jwt.logout-url}")
    private String logoutUrl;

    @Value("${security.jwt.secret-refresh-token}")
    private String secretSignInRefreshToken;

    @Value("${security.jwt.expiration-refresh-token}")
    private long expirationRefreshToken;

    @Value("${security.jwt.generate-refresh-token-url}")
    private String generateRefreshTokenUrl;
}
package com.andrei.mobiletracker.user.security;

import com.andrei.mobiletracker.security.config.JwtAuthorizationProviderConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RefreshScope
@Component
public class UserJwtAuthorizationProviderConfig extends UserBasicJwtConfig implements JwtAuthorizationProviderConfig {

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

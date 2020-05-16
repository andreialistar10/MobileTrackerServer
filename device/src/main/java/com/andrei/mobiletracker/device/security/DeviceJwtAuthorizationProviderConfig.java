package com.andrei.mobiletracker.device.security;

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
public class DeviceJwtAuthorizationProviderConfig extends DeviceBasicJwtSecurity implements JwtAuthorizationProviderConfig {

    @Value("${device.security.jwt.login-url}")
    private String loginUrl;

    @Value("${device.security.jwt.logout-url}")
    private String logoutUrl;

    @Value("${device.security.jwt.secret-refresh-token}")
    private String secretSignInRefreshToken;

    @Value("${device.security.jwt.expiration-refresh-token}")
    private long expirationRefreshToken;

    @Value("${device.security.jwt.generate-refresh-token-url}")
    private String generateRefreshTokenUrl;
}

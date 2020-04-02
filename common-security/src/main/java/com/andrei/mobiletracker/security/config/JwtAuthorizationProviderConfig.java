package com.andrei.mobiletracker.security.config;

public interface JwtAuthorizationProviderConfig extends BasicJwtConfig {

    String getLoginUrl();

    String getLogoutUrl();

    String getSecretSignInRefreshToken();

    long getExpirationRefreshToken();

    String getGenerateRefreshTokenUrl();
}
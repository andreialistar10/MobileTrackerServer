package com.andrei.mobiletracker.security.config;

public interface BasicJwtConfig {

    String getHeader();

    String getPrefixHeader();

    long getExpiration();

    String getSecretSignIn();
}
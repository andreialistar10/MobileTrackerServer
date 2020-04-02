package com.andrei.mobiletracker.security.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Getter
@ToString
@Component
@Primary
@RefreshScope
public class BasicJwtConfig {

    @Value("${user.security.jwt.header.name}")
    private String header;

    @Value("${user.security.jwt.header.prefix-value}")
    private String prefixHeader;

    @Value("${user.security.jwt.expiration}")
    private long expiration;

    @Value("${user.security.jwt.secret}")
    private String secretSignIn;
}
package com.andrei.mobiletracker.device.security;

import com.andrei.mobiletracker.security.config.BasicJwtConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@Getter
@Setter
public class UserBasicJwtSecurity implements BasicJwtConfig {

    @Value("${user.security.jwt.header.name}")
    private String header;

    @Value("${user.security.jwt.header.prefix-value}")
    private String prefixHeader;

    @Value("${user.security.jwt.expiration}")
    private long expiration;

    @Value("${user.security.jwt.secret}")
    private String secretSignIn;
}

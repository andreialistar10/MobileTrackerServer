package com.andrei.mobiletracker.location.security;

import com.andrei.mobiletracker.security.config.BasicJwtConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@Getter
@Setter
public class DeviceBasicJwtSecurityConfig implements BasicJwtConfig {

    @Value("${device.security.jwt.header.name}")
    private String header;

    @Value("${device.security.jwt.header.prefix-value}")
    private String prefixHeader;

    @Value("${device.security.jwt.expiration}")
    private long expiration;

    @Value("${device.security.jwt.secret}")
    private String secretSignIn;
}


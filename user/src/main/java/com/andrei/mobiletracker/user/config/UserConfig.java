package com.andrei.mobiletracker.user.config;

import com.andrei.mobiletracker.security.config.JwtAuthenticationConfig;
import com.andrei.mobiletracker.security.jwtFilter.authMicroserviceFilters.AuthJwtFilter;
import com.andrei.mobiletracker.security.jwtFilter.authMicroserviceFilters.util.AuthJwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public JwtAuthenticationConfig buildJwtAuthenticationConfig() {

        return new JwtAuthenticationConfig();
    }

    @Bean
    public AuthJwtUtil buildAuthJwtUtil() {

        return new AuthJwtUtil(buildJwtAuthenticationConfig());
    }

    @Bean
    public AuthJwtFilter buildJwtTokenAuthenticationFilter() {

        return new AuthJwtFilter(buildAuthJwtUtil(), buildJwtAuthenticationConfig());
    }
}

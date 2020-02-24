package com.andrei.mobiletracker.user.config;

import com.andrei.mobiletracker.security.config.JwtAuthenticationConfig;
import com.andrei.mobiletracker.security.jwtFilter.authMicroserviceFilters.AuthJwtFilter;
import com.andrei.mobiletracker.security.jwtFilter.authMicroserviceFilters.util.AuthJwtUtil;
import com.andrei.mobiletracker.user.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

        return new AuthJwtFilter(buildAuthJwtUtil());
    }
}

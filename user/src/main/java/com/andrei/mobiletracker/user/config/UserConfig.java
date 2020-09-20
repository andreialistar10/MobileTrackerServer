package com.andrei.mobiletracker.user.config;

import com.andrei.mobiletracker.security.config.JwtAuthorizationProviderConfig;
import com.andrei.mobiletracker.security.jwtFilter.authMicroserviceFilters.AuthJwtFilter;
import com.andrei.mobiletracker.security.jwtFilter.authMicroserviceFilters.util.AuthJwtUtil;
import com.andrei.mobiletracker.user.security.UserJwtAuthorizationProviderConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public JwtAuthorizationProviderConfig jwtAuthorizationProviderConfigBean() {

        return new UserJwtAuthorizationProviderConfig();
    }

    @Bean
    public AuthJwtUtil authJwtUtilBean(JwtAuthorizationProviderConfig jwtAuthorizationProviderConfigBean) {

        return new AuthJwtUtil(jwtAuthorizationProviderConfigBean);
    }

    @Bean
    public AuthJwtFilter buildJwtTokenAuthenticationFilter(AuthJwtUtil authJwtUtilBean, JwtAuthorizationProviderConfig jwtAuthorizationProviderConfigBean) {

        return new AuthJwtFilter(authJwtUtilBean, jwtAuthorizationProviderConfigBean);
    }
}

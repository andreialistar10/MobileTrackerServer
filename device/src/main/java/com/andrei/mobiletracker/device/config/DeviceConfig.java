package com.andrei.mobiletracker.device.config;

import com.andrei.mobiletracker.device.security.DeviceBasicJwtSecurity;
import com.andrei.mobiletracker.device.security.DeviceJwtAuthorizationProviderConfig;
import com.andrei.mobiletracker.device.security.UserBasicJwtSecurity;
import com.andrei.mobiletracker.security.config.BasicJwtConfig;
import com.andrei.mobiletracker.security.config.JwtAuthorizationProviderConfig;
import com.andrei.mobiletracker.security.jwtFilter.authMicroserviceFilters.util.AuthJwtUtil;
import com.andrei.mobiletracker.security.jwtFilter.microserviceFilters.JwtTokenAuthenticationFilter;
import com.andrei.mobiletracker.security.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeviceConfig {

    @Bean
    public JwtAuthorizationProviderConfig jwtAuthorizationProviderConfigBean() {
        return new DeviceJwtAuthorizationProviderConfig();
    }

    @Bean
    public AuthJwtUtil authJwtUtilBean(JwtAuthorizationProviderConfig jwtAuthorizationProviderConfigBean){
        return new AuthJwtUtil(jwtAuthorizationProviderConfigBean);
    }

    @Bean
    public JwtUtil jwtUtilBean() {
        return new JwtUtil();
    }

    @Bean
    public BasicJwtConfig userJwtConfigBean() {
        return new UserBasicJwtSecurity();
    }

    @Bean
    public BasicJwtConfig deviceJwtConfig() {
        return new DeviceBasicJwtSecurity();
    }

    @Bean
    public JwtAuthorizationProviderConfig deviceJwtAuthorizationProviderConfig() {
        return new DeviceJwtAuthorizationProviderConfig();
    }

    @Bean
    public JwtTokenAuthenticationFilter userAuthenticationFilter(BasicJwtConfig userJwtConfigBean, JwtUtil jwtUtilBean) {
        return new JwtTokenAuthenticationFilter(userJwtConfigBean, jwtUtilBean);
    }

    @Bean
    public JwtTokenAuthenticationFilter deviceAuthenticationFilter(BasicJwtConfig deviceJwtConfig, JwtUtil jwtUtilBean){
        return new JwtTokenAuthenticationFilter(deviceJwtConfig, jwtUtilBean);
    }
}

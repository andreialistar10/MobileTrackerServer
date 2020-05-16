package com.andrei.mobiletracker.device.config;

import com.andrei.mobiletracker.device.security.UserBasicJwtSecurity;
import com.andrei.mobiletracker.security.config.BasicJwtConfig;
import com.andrei.mobiletracker.security.jwtFilter.microserviceFilters.JwtTokenAuthenticationFilter;
import com.andrei.mobiletracker.security.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeviceConfig {

    @Bean
    public JwtUtil jwtUtilBean() {
        return new JwtUtil();
    }

    @Bean
    public BasicJwtConfig userJwtConfigBean() {
        return new UserBasicJwtSecurity();
    }

    @Bean
    public JwtTokenAuthenticationFilter userAuthenticationFilter(BasicJwtConfig userJwtConfigBean, JwtUtil jwtUtilBean) {
        return new JwtTokenAuthenticationFilter(userJwtConfigBean, jwtUtilBean);
    }
}

package com.andrei.mobiletracker.location.config;

import com.andrei.mobiletracker.location.security.DeviceBasicJwtSecurityConfig;
import com.andrei.mobiletracker.location.security.UserBasicJwtSecurityConfig;
import com.andrei.mobiletracker.security.config.BasicJwtConfig;
import com.andrei.mobiletracker.security.jwtFilter.microserviceFilters.JwtTokenAuthenticationFilter;
import com.andrei.mobiletracker.security.microservicesprovider.MicroserviceResponseProvider;
import com.andrei.mobiletracker.security.util.JwtUtil;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class LocationConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilderBean() {

        return WebClient.builder();
    }

    @Bean
    public MicroserviceResponseProvider microservicesResponseProvider(WebClient.Builder webClientBuilderBean){
        return new MicroserviceResponseProvider(webClientBuilderBean);
    }

    @Bean
    public BasicJwtConfig userJwtConfigBean() {
        return new UserBasicJwtSecurityConfig();
    }

    @Bean
    public BasicJwtConfig deviceJwtConfig() {
        return new DeviceBasicJwtSecurityConfig();
    }

    @Bean
    public JwtUtil jwtUtilBean() {
        return new JwtUtil();
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

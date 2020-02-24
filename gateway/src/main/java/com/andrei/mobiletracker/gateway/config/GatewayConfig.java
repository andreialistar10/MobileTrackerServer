package com.andrei.mobiletracker.gateway.config;

import com.andrei.mobiletracker.gateway.filters.PreFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public PreFilter buildPreFilter() {

        return new PreFilter();
    }
}

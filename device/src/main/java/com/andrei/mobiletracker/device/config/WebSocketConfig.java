package com.andrei.mobiletracker.device.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public final static String DEVICE_TOPIC = "/devices";
    public final static String ERROR_TOPIC = "/errors";
    public final static String USER_TOPIC = "/users";
    private final static String APPLICATION_PREFIX = "/device-connectivity";
    private final static String STOMP_ENDPOINT = "/mobile-tracker/device-connectivity";

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(DEVICE_TOPIC, USER_TOPIC, ERROR_TOPIC);
        config.setApplicationDestinationPrefixes(APPLICATION_PREFIX);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(STOMP_ENDPOINT)
                .setAllowedOrigins("*")
                .withSockJS();
    }
}

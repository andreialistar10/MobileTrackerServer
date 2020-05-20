package com.andrei.mobiletracker.device.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    public final static String DEVICE_TOPIC = "/devices";
    public final static String ERROR_TOPIC = "/errors";
    public final static String USER_TOPIC = "/users";
    private final static String APPLICATION_PREFIX = "/device-connectivity";
    private final static String STOMP_ENDPOINT = "/mobile-tracker/device-connectivity";

    @Autowired
    private ChannelInterceptor channelInterceptor;

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

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(channelInterceptor);
    }
}

package com.andrei.mobiletracker.device.stomp.interceptor;

import com.andrei.mobiletracker.security.config.BasicJwtConfig;
import com.andrei.mobiletracker.security.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorizationChannelInterceptorAdapter implements ChannelInterceptor {

    private static final Logger logger = LogManager.getLogger(AuthorizationChannelInterceptorAdapter.class);

    @Autowired
    @Qualifier(value = "authJwtUtilBean")
    private JwtUtil jwtUtil;

    @Autowired
    private BasicJwtConfig deviceJwtConfig;

    @Override
    public Message<?> preSend(@NonNull Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null || !StompCommand.CONNECT.equals(accessor.getCommand())) {
            return message;
        }
        logger.info("------------------LOGGING BEFORE STOMP CLIENT CONNECTING------------------");
        try {
            String token = getToken(accessor);
            Authentication stompClient = createAuthentication(token);
            accessor.setUser(stompClient);
            accessor.setLeaveMutable(true);
            logger.info("SUBJECT: {}", stompClient.getName());
            logger.info("-----------------SUCCESSFUL BEFORE STOMP CLIENT CONNECTING-----------------");
            return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
        } catch (Exception e) {
            logger.info("SUBJECT: ANONYMOUS STOMP CLIENT");
            logger.info("-----------------SUCCESSFUL BEFORE STOMP CLIENT CONNECTING-----------------");
            return message;
        }
    }

    private String getToken(StompHeaderAccessor accessor) {

        List<String> tokenList = accessor.getNativeHeader("X-Authorization");
        if (tokenList == null || tokenList.size() < 1) {
            return null;
        }
        return tokenList.get(0);
    }

    private Authentication createAuthentication(String token) {

        Claims claims = jwtUtil.extractAllClaims(token, deviceJwtConfig.getSecretSignIn());
        String deviceId = claims.getSubject();
        List<GrantedAuthority> authorities = getAuthorities(claims);
        return new UsernamePasswordAuthenticationToken(deviceId, "", authorities);
    }

    private List<GrantedAuthority> getAuthorities(Claims claims) {

        List<String> authorities = claims.get("authorities", List.class);
        if (authorities == null) {
            return new ArrayList<>();
        }
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

package com.andrei.mobiletracker.security.jwtFilter.microserviceFilters;


import com.andrei.mobiletracker.security.config.BasicJwtConfig;
import com.andrei.mobiletracker.security.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private BasicJwtConfig config;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String token = httpServletRequest.getHeader(config.getHeader());

        if (token != null) {
            token = token.replace(config.getPrefixHeader() + " ", "");
            try {
                Claims claims = jwtUtil.extractAllClaims(token, config.getSecretSignIn());
                String username = claims.getSubject();
                List<String> authorities = claims.get("authorities", List.class);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    setAuthenticatedUser(username, authorities);
                }
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void setAuthenticatedUser(String username, List<String> authorities) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, null,
                convertStringAuthorityToSimpleGrantedAuthority(authorities)
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private Collection<SimpleGrantedAuthority> convertStringAuthorityToSimpleGrantedAuthority(List<String> authorities) {

        return authorities.stream()
                .filter(Objects::nonNull)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
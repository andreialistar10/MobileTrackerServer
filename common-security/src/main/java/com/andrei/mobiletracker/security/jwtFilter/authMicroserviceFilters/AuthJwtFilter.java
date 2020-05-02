package com.andrei.mobiletracker.security.jwtFilter.authMicroserviceFilters;

import com.andrei.mobiletracker.security.config.JwtAuthorizationProviderConfig;
import com.andrei.mobiletracker.security.jwtFilter.authMicroserviceFilters.util.AuthJwtUtil;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class AuthJwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService service;

    @Autowired
    private AuthJwtUtil jwtUtil;

    @Autowired
    private JwtAuthorizationProviderConfig config;

    private static final Logger logger = LogManager.getLogger(AuthJwtFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("---------------------- REQUEST TO {} ----------------------",httpServletRequest.getRequestURI());
        if (skipIfLogin(httpServletRequest, httpServletResponse, filterChain))
            return;

        final String authorizationHeader = httpServletRequest.getHeader(config.getHeader());

        String username = null;
        String jwt = null;
        List<String> authorities = null;
        String secretSignIn = null;
        if (authorizationHeader != null && authorizationHeader.startsWith(config.getPrefixHeader() + " ")) {
            jwt = authorizationHeader.substring(config.getPrefixHeader().length() + 1);
            String requestURI = httpServletRequest.getRequestURI();
            if (requestURI.equals(config.getGenerateRefreshTokenUrl()) || requestURI.equals(config.getLogoutUrl())) {
                logger.info("Request with refresh token");
                username = jwtUtil.extractUsername(jwt, config.getSecretSignInRefreshToken());
                authorities = jwtUtil.extractAllClaims(jwt, config.getSecretSignInRefreshToken()).get("authorities", List.class);
                secretSignIn = config.getSecretSignInRefreshToken();
            }
            else {
                logger.info("Request with simple token");
                username = jwtUtil.extractUsername(jwt, config.getSecretSignIn());
                authorities = jwtUtil.extractAllClaims(jwt, config.getSecretSignIn()).get("authorities", List.class);
                secretSignIn = config.getSecretSignIn();
            }
        }

        loginUserAlreadyLoggedInAndValidToken(httpServletRequest, username, jwt, authorities, secretSignIn);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void loginUserAlreadyLoggedInAndValidToken(@NonNull HttpServletRequest httpServletRequest, String username, String jwt, List<String> authorities, String secretSignIn) {

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (authorities.size() > 0) {
                UserDetails userDetails = service.loadUserByUsername(username);
                if (authorities.get(0).equals("REFRESH_TOKEN")) {
                    userDetails = new User(username, userDetails.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("REFRESH_TOKEN")));
                }
                loginUserIfValidToken(httpServletRequest, userDetails, jwt, secretSignIn);
            }
        }
    }

    private void loginUserIfValidToken(@NonNull HttpServletRequest httpServletRequest, UserDetails userDetails, String jwt, String secretSignIn) {

        if (jwtUtil.validateToken(jwt, userDetails, secretSignIn)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    private boolean skipIfLogin(@NonNull HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain) throws IOException, ServletException {

        //if the request is for login purpose, then this filter will be omitted
        if (httpServletRequest.getRequestURI().equals(config.getLoginUrl())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return true;
        }
        return false;
    }
}
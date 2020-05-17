package com.andrei.mobiletracker.security.jwtFilter.authMicroserviceFilters.util;

import com.andrei.mobiletracker.security.config.JwtAuthorizationProviderConfig;
import com.andrei.mobiletracker.security.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AuthJwtUtil extends JwtUtil {

    private JwtAuthorizationProviderConfig config;

    public AuthJwtUtil(JwtAuthorizationProviderConfig config) {
        this.config = config;
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        List<String> authorities = generateAuthoritiesList(userDetails);
        claims.put("authorities", authorities);
        return createToken(claims, userDetails.getUsername(), config.getExpiration(), config.getSecretSignIn());
    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        List<String> generate = Collections.singletonList("REFRESH_TOKEN");
        claims.put("authorities", generate);
        return createToken(claims, username, config.getExpirationRefreshToken(), config.getSecretSignInRefreshToken());
    }

    public boolean validateToken(String token, UserDetails userDetails, String secretSignIn) {

        final String username = extractUsername(token, secretSignIn);
        return !isTokenExpired(token, secretSignIn) && username != null && username.equals(userDetails.getUsername());
    }

    public String extractUsername(String token, String secretSignIn) {

        return extractClaim(token, secretSignIn, Claims::getSubject);
    }

    private Date extractExpiration(String token, String secretSignIn) {

        return extractClaim(token, secretSignIn, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, String secretSignIn, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token, secretSignIn);
        return claimsResolver.apply(claims);
    }

    private String createToken(Map<String, Object> claims, String username, long tokenValidTime, String secretSignIn) {

        final long currentTimestamp = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(currentTimestamp))
                .setExpiration(new Date(currentTimestamp + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretSignIn)
                .compact();
    }

    private boolean isTokenExpired(String token, String secretSignIn) {

        return extractExpiration(token, secretSignIn).before(new Date());
    }

    private List<String> generateAuthoritiesList(UserDetails userDetails) {

        return userDetails
                .getAuthorities()
                .stream()
                .filter(Objects::nonNull)
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
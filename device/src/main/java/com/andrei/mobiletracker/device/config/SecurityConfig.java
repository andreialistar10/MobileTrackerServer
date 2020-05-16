package com.andrei.mobiletracker.device.config;

import com.andrei.mobiletracker.security.jwtFilter.microserviceFilters.JwtTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenAuthenticationFilter authJwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf()
                .disable()
                .logout().disable()
                .formLogin().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/devices/**",
                        "/swagger-ui.html/**",
                        "/configuration/**",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/api/**").permitAll()
                .antMatchers("/my-devices/**").hasAuthority("ACTIVATED_ACCOUNT")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .exceptionHandling().authenticationEntryPoint(
                (httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterBefore(authJwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

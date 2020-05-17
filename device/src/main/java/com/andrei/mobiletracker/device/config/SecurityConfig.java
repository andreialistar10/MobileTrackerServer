package com.andrei.mobiletracker.device.config;

import com.andrei.mobiletracker.device.security.DeviceAuthority;
import com.andrei.mobiletracker.security.jwtFilter.microserviceFilters.JwtTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Order(1)
    @Configuration
    public static class UnregisteredDeviceApiConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier(value = "deviceAuthenticationFilter")
        private JwtTokenAuthenticationFilter deviceAuthJwtFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.antMatcher("/unregistered-devices/**")
                    .csrf().disable()
                    .logout().disable()
                    .formLogin().disable()
                    .cors().and()
                    .authorizeRequests()
                    .antMatchers("/unregistered-devices/confirm-pairing").hasAuthority(DeviceAuthority.UNREGISTERED_DEVICE.toString())
                    .antMatchers("/unregistered-devices/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .anonymous()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(
                    (httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                    .and()
                    .addFilterBefore(deviceAuthJwtFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }

    @Configuration
    @Order(2)
    public static class UserSecurityApiConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier(value = "userAuthenticationFilter")
        private JwtTokenAuthenticationFilter authJwtFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.antMatcher("/my-devices/**")
                    .csrf()
                    .disable()
                    .logout().disable()
                    .formLogin().disable()
                    .cors().and()
                    .authorizeRequests()
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

    @Configuration
    @Order(3)
    public static class DefaultSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.antMatcher("/**")
                    .csrf()
                    .disable()
                    .logout().disable()
                    .formLogin().disable()
                    .cors().and()
                    .authorizeRequests()
                    .antMatchers("/swagger-ui.html/**",
                            "/configuration/**",
                            "/swagger-resources/**",
                            "/v2/api-docs",
                            "/webjars/**",
                            "/api/**")
                    .permitAll();
        }
    }
}

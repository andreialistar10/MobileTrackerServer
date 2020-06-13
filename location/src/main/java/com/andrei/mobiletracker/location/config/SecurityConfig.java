package com.andrei.mobiletracker.location.config;

import com.andrei.mobiletracker.beans.authorities.DeviceAuthority;
import com.andrei.mobiletracker.beans.authorities.UserAuthority;
import com.andrei.mobiletracker.security.jwtFilter.microserviceFilters.JwtTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Order(1)
    @Configuration
    public static class DeviceSecurityApiConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier(value = "deviceAuthenticationFilter")
        private JwtTokenAuthenticationFilter deviceAuthJwtFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.antMatcher("/locations")
                    .csrf().disable()
                    .logout().disable()
                    .formLogin().disable()
                    .cors().and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/locations").hasAuthority(DeviceAuthority.REGISTERED_DEVICE.toString())
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

            http.antMatcher("/locations/**")
                    .csrf()
                    .disable()
                    .logout().disable()
                    .formLogin().disable()
                    .cors().and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/locations/**").hasAuthority(UserAuthority.ACTIVATED_ACCOUNT.toString())
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

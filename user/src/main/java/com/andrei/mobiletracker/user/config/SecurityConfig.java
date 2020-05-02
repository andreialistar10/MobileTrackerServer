package com.andrei.mobiletracker.user.config;

import com.andrei.mobiletracker.security.config.JwtAuthorizationProviderConfig;
import com.andrei.mobiletracker.security.jwtFilter.authMicroserviceFilters.AuthJwtFilter;
import com.andrei.mobiletracker.user.model.UserAccountRole;
import com.andrei.mobiletracker.user.service.impl.LoginServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginServiceImpl service;
    private final AuthJwtFilter authJwtFilter;
    private final JwtAuthorizationProviderConfig config;

    public SecurityConfig(JwtAuthorizationProviderConfig config, AuthJwtFilter authJwtFilter, LoginServiceImpl service) {

        this.config = config;
        this.authJwtFilter = authJwtFilter;
        this.service = service;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(service);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf()
                .disable()
                .logout().disable()
                .formLogin().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers(config.getLoginUrl(),
                        "/users/sign-up",
                        "/users/confirm-account",
                        "/swagger-ui.html/**",
                        "/configuration/**",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/api/**").permitAll()
                .antMatchers("/users/resend-registration-email").hasAuthority(UserAccountRole.NOT_ACTIVATED_ACCOUNT.toString())
                .antMatchers("/refresh-token").hasAuthority("REFRESH_TOKEN")
                .antMatchers("/logout").hasAuthority("REFRESH_TOKEN")
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

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }
}

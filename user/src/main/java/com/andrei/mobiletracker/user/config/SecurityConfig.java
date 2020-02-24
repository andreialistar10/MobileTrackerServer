package com.andrei.mobiletracker.user.config;

import com.andrei.mobiletracker.security.config.JwtAuthenticationConfig;
import com.andrei.mobiletracker.security.jwtFilter.authMicroserviceFilters.AuthJwtFilter;
import com.andrei.mobiletracker.user.model.MyUserRoleType;
import com.andrei.mobiletracker.user.service.impl.LoginServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginServiceImpl service;
    private final AuthJwtFilter authJwtFilter;
    private final JwtAuthenticationConfig config;

    public SecurityConfig(JwtAuthenticationConfig config, AuthJwtFilter authJwtFilter, LoginServiceImpl service) {

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
                .antMatchers("/users/resend-registration-email").hasAuthority(MyUserRoleType.NOT_ACTIVATED_ACCOUNT.toString())
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .exceptionHandling().authenticationEntryPoint(
                (httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterBefore(authJwtFilter, UsernamePasswordAuthenticationFilter.class); //execute this filter before verifying if user is authenticated
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }
}

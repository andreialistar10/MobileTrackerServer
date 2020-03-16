package com.andrei.mobiletracker.user.service.impl;

import com.andrei.mobiletracker.security.jwtFilter.authMicroserviceFilters.util.AuthJwtUtil;
import com.andrei.mobiletracker.user.dao.user.UserDao;
import com.andrei.mobiletracker.user.dto.LoggedInActivatedAccountUserDto;
import com.andrei.mobiletracker.user.dto.LoggedInUserDto;
import com.andrei.mobiletracker.user.dto.UserAccountDto;
import com.andrei.mobiletracker.user.model.UserAccount;
import com.andrei.mobiletracker.user.model.UserAccountRoleType;
import com.andrei.mobiletracker.user.service.LoginService;
import com.andrei.mobiletracker.user.service.exception.UserExceptionType;
import com.andrei.mobiletracker.user.service.exception.UserServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Primary
@Component
public class LoginServiceImpl implements LoginService, UserDetailsService {

    @Autowired
    private AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final AuthJwtUtil jwtUtil;
    private static final Logger logger = LogManager.getLogger(LoginServiceImpl.class);

    public LoginServiceImpl(UserDao userDao, AuthJwtUtil jwtUtil) {

        logger.info("------------------INIT  LoginServiceImpl------------------");
        this.userDao = userDao;
        this.jwtUtil = jwtUtil;
        logger.info("-------------SUCCESSFUL INIT LoginServiceImpl-------------");
    }

    @Override
    public LoggedInUserDto login(UserAccountDto user) {

        logger.info("------------------LOGGING  login------------------");
        logger.info("{} trying to connect", user.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            final String role = userDetails.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse(null);
            final String jwt = jwtUtil.generateToken(userDetails);
            LoggedInUserDto loggedInUserDto = null;
            if (UserAccountRoleType.ACTIVATED_ACCOUNT.toString().equals(role))
                loggedInUserDto = generateLoggedInActivatedAccountUserDto(jwt,role, userDetails.getUsername());
            else
                loggedInUserDto = generateLoggedInUserDto(jwt,role,userDetails.getUsername());
            logger.info("-----------------SUCCESSFUL login-----------------");
            return loggedInUserDto;
        } catch (BadCredentialsException exception) {
            logger.error("ERROR IN LOGIN: {}", exception.getMessage());
            throw new UserServiceException("Incorrect username or password", HttpStatus.NOT_FOUND, UserExceptionType.INVALID_CREDENTIALS);
        }
    }

    private LoggedInUserDto generateLoggedInUserDto(String jwt, String role, String username) {

        return LoggedInUserDto.builder()
                .jwt(jwt)
                .role(role)
                .build();
    }

    private LoggedInUserDto generateLoggedInActivatedAccountUserDto(String jwt, String role, String username) {

        final String refreshToken = jwtUtil.generateRefreshToken(username);
        return new LoggedInActivatedAccountUserDto(jwt,role,refreshToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("+++++++++++++++++++++++++++++++LOGGING loadUserByUsername+++++++++++++++++++++++++++++++");
        UserAccount user = userDao.findOneMyUserByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Doesn't exist an user with username " + username);
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getType().toString()));
        logger.info("+++++++++++++++++++++++++++++++SUCCESSFUL LOGGING loadUserByUsername+++++++++++++++++++++++++++++++");
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}

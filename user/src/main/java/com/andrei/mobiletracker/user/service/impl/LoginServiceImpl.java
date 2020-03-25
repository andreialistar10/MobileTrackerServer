package com.andrei.mobiletracker.user.service.impl;

import com.andrei.mobiletracker.security.jwtFilter.authMicroserviceFilters.util.AuthJwtUtil;
import com.andrei.mobiletracker.user.dao.refreshtoken.RefreshTokenDao;
import com.andrei.mobiletracker.user.dao.user.UserDao;
import com.andrei.mobiletracker.user.model.RefreshToken;
import com.andrei.mobiletracker.user.model.UserAccount;
import com.andrei.mobiletracker.user.model.UserAccountRole;
import com.andrei.mobiletracker.user.model.UserAccountToken;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Primary
@Component
public class LoginServiceImpl implements LoginService, UserDetailsService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final RefreshTokenDao refreshTokenDao;
    private final AuthJwtUtil jwtUtil;
    private static final Logger logger = LogManager.getLogger(LoginServiceImpl.class);

    public LoginServiceImpl(UserDao userDao, RefreshTokenDao refreshTokenDao, AuthJwtUtil jwtUtil) {

        logger.info("------------------INIT  LoginServiceImpl------------------");
        this.userDao = userDao;
        this.refreshTokenDao = refreshTokenDao;
        this.jwtUtil = jwtUtil;
        logger.info("-------------SUCCESSFUL INIT LoginServiceImpl-------------");
    }

    @Override
    public UserAccountToken login(UserAccount userAccount) {

        logger.info("------------------LOGGING  login------------------");
        logger.info("{} trying to connect", userAccount.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userAccount.getUsername(), userAccount.getPassword())
            );
            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            final String role = getRoleFromUserDetails(userDetails);
            userAccount.setRole(UserAccountRole.valueOf(role));
            userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
            final String jwt = jwtUtil.generateToken(userDetails);
            UserAccountToken userAccountToken = UserAccountToken.builder()
                    .jwt(jwt)
                    .build();
            if (UserAccountRole.ACTIVATED_ACCOUNT.toString().equals(role)) {
                userAccountToken.setRefreshToken(jwtUtil.generateRefreshToken(userAccount.getUsername()));
                RefreshToken refreshToken = RefreshToken.builder()
                        .userAccount(userAccount)
                        .token(userAccountToken.getRefreshToken())
                        .build();
                refreshTokenDao.saveOneRefreshToken(refreshToken);
            }
            logger.info("-----------------SUCCESSFUL login-----------------");
            return userAccountToken;
        } catch (BadCredentialsException exception) {
            logger.error("ERROR IN LOGIN: {}", exception.getMessage());
            throw new UserServiceException("Incorrect username or password", HttpStatus.NOT_FOUND, UserExceptionType.INVALID_CREDENTIALS);
        }
    }

    @Override
    public RefreshToken findOneRefreshTokenByUsernameAndJwtRefreshToken(String username, String jwtRefreshToken) {

        logger.info("------------------LOGGING  findOneRefreshTokenByUsernameAndJwtRefreshToken------------------");
        RefreshToken refreshToken = refreshTokenDao.findOneRefreshTokenByUsernameAndJwtRefreshToken(username,jwtRefreshToken);
        logger.info("-----------------SUCCESSFUL findOneRefreshTokenByUsernameAndJwtRefreshToken-----------------");
        return refreshToken;
    }

    @Transactional
    @Override
    public UserAccountToken generateNewRefreshToken(RefreshToken refreshToken) {

        if (refreshToken == null){
            throw new UserServiceException("Invalid refresh token",HttpStatus.UNAUTHORIZED,UserExceptionType.INVALID_REFRESH_TOKEN);
        }

        if (refreshTokenDao.deleteOneRefreshTokenByToken(refreshToken.getToken()) != 1){
            throw new UserServiceException("Invalid refresh token",HttpStatus.UNAUTHORIZED,UserExceptionType.INVALID_REFRESH_TOKEN);
        }

        UserAccount userAccount = refreshToken.getUserAccount();
        String newRefreshToken = jwtUtil.generateRefreshToken(userAccount.getUsername());
        refreshToken.setToken(newRefreshToken);
        refreshTokenDao.saveOneRefreshToken(refreshToken);
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(userAccount.getRole().toString()));
        UserDetails userDetails = new User(userAccount.getUsername(),userAccount.getPassword(), authorities);
        final String jwt = jwtUtil.generateToken(userDetails);
        return UserAccountToken.builder()
                .jwt(jwt)
                .refreshToken(newRefreshToken)
                .build();
    }

    @Transactional
    @Override
    public void logout(RefreshToken refreshToken) {

        if (refreshToken == null){
            throw new UserServiceException("Invalid refresh token",HttpStatus.UNAUTHORIZED,UserExceptionType.INVALID_REFRESH_TOKEN);
        }

        if (refreshTokenDao.deleteOneRefreshTokenByToken(refreshToken.getToken()) != 1){
            throw new UserServiceException("Invalid refresh token",HttpStatus.UNAUTHORIZED,UserExceptionType.INVALID_REFRESH_TOKEN);
        }
    }

    private String getRoleFromUserDetails(UserDetails userDetails) {

        return userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("+++++++++++++++++++++++++++++++LOGGING loadUserByUsername+++++++++++++++++++++++++++++++");
        UserAccount user = userDao.findOneUserAccountByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Doesn't exist an user with username " + username);
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));
        logger.info("+++++++++++++++++++++++++++++++SUCCESSFUL LOGGING loadUserByUsername+++++++++++++++++++++++++++++++");
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}

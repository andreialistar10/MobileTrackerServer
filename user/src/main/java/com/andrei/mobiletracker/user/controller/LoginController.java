package com.andrei.mobiletracker.user.controller;

import com.andrei.mobiletracker.security.config.BasicJwtConfig;
import com.andrei.mobiletracker.user.dto.LoggedInUserData;
import com.andrei.mobiletracker.user.dto.UserAccountData;
import com.andrei.mobiletracker.user.facade.login.LoginFacade;
import com.andrei.mobiletracker.user.service.exception.UserExceptionType;
import com.andrei.mobiletracker.user.service.exception.UserServiceException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("")
public class LoginController {

    private final LoginFacade loginFacade;
    private final BasicJwtConfig basicJwtConfig;
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    public LoginController(LoginFacade loginFacade, BasicJwtConfig basicJwtConfig) {

        this.loginFacade = loginFacade;
        this.basicJwtConfig = basicJwtConfig;
    }

    @ApiOperation(value = "Login an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = LoggedInUserData.class),
            @ApiResponse(code = 404, message = "INVALID_CREDENTIALS", response = UserExceptionType.class),
    })
    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<LoggedInUserData> login(@Valid UserAccountData userAccountData, BindingResult result) {

        logger.info("------------------LOGGING  login------------------");
        logger.info("username: {}", userAccountData.getUsername());
        if (result.hasErrors())
            throw new UserServiceException("Username or password for user: " + userAccountData.getUsername() + "can not be null!", HttpStatus.NOT_FOUND, UserExceptionType.INVALID_USER_DETAILS);
        LoggedInUserData loggedInUserData = loginFacade.login(userAccountData);
        logger.info("-----------------SUCCESSFUL login-----------------");
        return new ResponseEntity<>(loggedInUserData, HttpStatus.OK);
    }

    @ApiOperation(value = "Login an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = LoggedInUserData.class),
            @ApiResponse(code = 400, message = "INVALID_REFRESH_TOKEN", response = UserExceptionType.class),
    })
    @RequestMapping(value = "/refresh-token",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoggedInUserData> generateNewToken(Principal principal, @RequestHeader HttpHeaders httpHeaders) {

        logger.info("------------------LOGGING  generateNewToken------------------");
        logger.info("username: {}", principal.getName());
        String jwtRefreshToken = getJwtRefreshTokenFromHttpHeaders(httpHeaders);
        LoggedInUserData loggedInUserData = loginFacade.generateNewRefreshToken(principal.getName(), jwtRefreshToken);
        logger.info("-----------------SUCCESSFUL generateNewToken-----------------");
        return new ResponseEntity<>(loggedInUserData, HttpStatus.OK);
    }

    @ApiOperation(value = "Login an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = UserExceptionType.class),
            @ApiResponse(code = 400, message = "INVALID_REFRESH_TOKEN", response = UserExceptionType.class),
    })
    @RequestMapping(value = "/logout",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserExceptionType> logout(Principal principal, @RequestHeader HttpHeaders httpHeaders) {

        logger.info("------------------LOGGING  logout------------------");
        logger.info("username: {}", principal.getName());
        String jwtRefreshToken = getJwtRefreshTokenFromHttpHeaders(httpHeaders);
        loginFacade.logout(principal.getName(), jwtRefreshToken);
        logger.info("-----------------SUCCESSFUL logout-----------------");
        return new ResponseEntity<>(UserExceptionType.SUCCESS, HttpStatus.OK);
    }

    private String getJwtRefreshTokenFromHttpHeaders(@RequestHeader HttpHeaders httpHeaders) {

        return httpHeaders.get(basicJwtConfig.getHeader())
                .stream()
                .findFirst()
                .map(authorizationHeaderValue -> authorizationHeaderValue.substring(basicJwtConfig.getPrefixHeader().length() + 1))
                .orElse("");
    }

    @ExceptionHandler({UserServiceException.class})
    @ResponseBody
    public ResponseEntity<UserExceptionType> handleException(UserServiceException exception) {

        logger.error("------------------LOGGING  handleException------------------");
        logger.error("Code: {}", exception.getHttpStatus());
        logger.error("Message: {}", exception.getMessage());
        logger.error("-----------------SUCCESSFUL handleException-----------------");
        return new ResponseEntity<>(exception.getType(), new HttpHeaders(), exception.getHttpStatus());
    }
}

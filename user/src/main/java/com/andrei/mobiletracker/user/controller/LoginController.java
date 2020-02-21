package com.andrei.mobiletracker.user.controller;

import com.andrei.mobiletracker.user.dto.LoggedInUserDto;
import com.andrei.mobiletracker.user.dto.UserDto;
import com.andrei.mobiletracker.user.service.LoginService;
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

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    public LoginController(LoginService loginService) {

        this.loginService = loginService;
    }

    @ApiOperation(value = "Login an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = LoggedInUserDto.class),
            @ApiResponse(code = 404, message = "INVALID_CREDENTIALS", response = UserExceptionType.class),
    })
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<LoggedInUserDto> login(@Valid UserDto userDto, BindingResult result) {

        logger.info("------------------LOGGING  login------------------");
        logger.info("username: {}", userDto.getUsername());
        if (result.hasErrors())
            throw new UserServiceException("Username or password for user: " + userDto.getUsername() + "can not be null!", HttpStatus.NOT_FOUND, UserExceptionType.INVALID_USER_DETAILS);
        LoggedInUserDto loggedInUserDto = loginService.login(userDto);
        logger.info("-----------------SUCCESSFUL login-----------------");
        return new ResponseEntity<>(loggedInUserDto, HttpStatus.OK);
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

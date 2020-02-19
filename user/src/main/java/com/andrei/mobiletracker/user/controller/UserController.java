package com.andrei.mobiletracker.user.controller;

import com.andrei.mobiletracker.user.dto.MyUserDetailRequestDto;
import com.andrei.mobiletracker.user.dto.MyUserDetailResponseDto;
import com.andrei.mobiletracker.user.model.MyUserDetail;
import com.andrei.mobiletracker.user.service.UserService;
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
@RequestMapping("")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @ApiOperation(value = "Sign-up a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = MyUserDetailResponseDto.class),
            @ApiResponse(code = 400, message = "DUPLICATE_USER", response = UserExceptionType.class),
            @ApiResponse(code = 400, message = "INVALID_USER_DETAILS", response = UserExceptionType.class),
    })
    @RequestMapping(value = "/sign-up", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MyUserDetailResponseDto> signup(@Valid @RequestBody MyUserDetailRequestDto myUserDetailRequestDto, BindingResult result) {

        logger.info("------------------LOGGING  signup------------------");
        logMyUserDetailRequestDto(myUserDetailRequestDto);
        if (result.hasErrors())
            throw new UserServiceException("User details for user: " + myUserDetailRequestDto.getUsername() + "are invalid", HttpStatus.BAD_REQUEST, UserExceptionType.INVALID_USER_DETAILS);
        MyUserDetail myUserDetail = userService.signup(myUserDetailRequestDto);
        MyUserDetailResponseDto responseDto = MyUserDetailResponseDto.builder()
                .email(myUserDetail.getEmail())
                .firstName(myUserDetail.getFirstName())
                .lastName(myUserDetail.getLastName())
                .username(myUserDetail.getUser().getUsername())
                .build();
        logger.info("-----------------SUCCESSFUL signup-----------------");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    private void logMyUserDetailRequestDto(MyUserDetailRequestDto myUserDetailRequestDto) {

        logger.info("username:    {}", myUserDetailRequestDto.getUsername());
        logger.info("password:    {}", myUserDetailRequestDto.getPassword());
        logger.info("re-password: {}", myUserDetailRequestDto.getRepeatPassword());
        logger.info("first name:  {}", myUserDetailRequestDto.getFirstName());
        logger.info("last name:   {}", myUserDetailRequestDto.getLastName());
        logger.info("e-mail:      {}", myUserDetailRequestDto.getEmail());
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

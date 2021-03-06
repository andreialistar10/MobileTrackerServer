package com.andrei.mobiletracker.user.controller;

import com.andrei.mobiletracker.user.dto.user.*;
import com.andrei.mobiletracker.user.model.UserAccountDetails;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @ApiOperation(value = "Sign-up a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = UserAccountDetailResponseDto.class),
            @ApiResponse(code = 400, message = "DUPLICATE_USER", response = UserExceptionType.class),
            @ApiResponse(code = 400, message = "INVALID_USER_DETAILS", response = UserExceptionType.class),
    })
    @RequestMapping(value = "/sign-up", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAccountDetailResponseDto> signup(@Valid @RequestBody UserAccountDetailRequestDto userAccountDetailRequestDto, BindingResult result) {

        logger.info("------------------LOGGING  signup------------------");
        logMyUserDetailRequestDto(userAccountDetailRequestDto);
        if (result.hasErrors())
            throw new UserServiceException("User details for user: " + userAccountDetailRequestDto.getUsername() + "are invalid", HttpStatus.BAD_REQUEST, UserExceptionType.INVALID_USER_DETAILS);
        UserAccountDetails userAccountDetails = userService.signup(userAccountDetailRequestDto);
        UserAccountDetailResponseDto responseDto = UserAccountDetailResponseDto.builder()
                .email(userAccountDetails.getEmail())
                .firstName(userAccountDetails.getFirstName())
                .lastName(userAccountDetails.getLastName())
                .username(userAccountDetails.getUser().getUsername())
                .build();
        logger.info("-----------------SUCCESSFUL signup-----------------");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Activate a NON_ACTIVATED_ACCOUNT")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = ActivatedUserDto.class),
            @ApiResponse(code = 404, message = "NOT_FOUND", response = UserExceptionType.class),
    })
    @RequestMapping(value = "/confirm-account",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            params = {"token"})
    public ResponseEntity<ActivatedUserDto> confirmAccount(@RequestParam(name = "token") String token) {

        logger.info("------------------LOGGING  confirmAccount------------------");
        ActivatedUserDto activatedAccount = userService.activateAccount(token);
        logger.info("-----------------SUCCESSFUL confirmAccount-----------------");
        logger.info("Account with username: {} has been activated", activatedAccount.getUsername());
        return new ResponseEntity<>(activatedAccount, HttpStatus.OK);
    }

    @ApiOperation(value = "Resend registration account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = UserExceptionType.class),
            @ApiResponse(code = 400, message = "INVALID_REQUEST", response = UserExceptionType.class),
            @ApiResponse(code = 500, message = "ERROR", response = UserExceptionType.class),
    })
    @RequestMapping(value = "/resend-registration-email", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserExceptionType> resendRegistrationAccount(Principal principal) {

        logger.info("------------------LOGGING  resendRegistrationAccount------------------");
        logger.info("username: {}", principal.getName());
        userService.resendRegistrationAccount(principal.getName());
        logger.info("-----------------SUCCESSFUL resendRegistrationAccount-----------------");
        return new ResponseEntity<>(UserExceptionType.SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieve my user details")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = UserDetails.class)
    })
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAccountDetailsData> getMyUserDetails(Principal principal) {

        logger.info("------------------LOGGING  getMyUserDetails------------------");
        logger.info("username: {}", principal.getName());
        UserAccountDetailsData userAccountDetailsData = userService.getUserAccountDetails(principal.getName());
        logger.info("-----------------SUCCESSFUL getMyUserDetails-----------------");
        return new ResponseEntity <>(userAccountDetailsData, HttpStatus.OK);
    }

    @ApiOperation(value = "Update my user details")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = UserDetails.class),
            @ApiResponse(code = 400, message = "INVALID_USER_DETAILS", response = UserExceptionType.class)
    })
    @RequestMapping(value = "",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAccountDetailsData> updateUserDetails(@Validated @RequestBody UpdatableUserAccountDetailsData updatableUserAccountDetailsData, BindingResult result, Principal principal) {

        logger.info("------------------LOGGING  updateUserDetails------------------");
        logger.info("username: {}", principal.getName());
        if (result.hasErrors()){
            throw new UserServiceException("User details for user: " + principal.getName() + "are invalid", HttpStatus.BAD_REQUEST, UserExceptionType.INVALID_USER_DETAILS);
        }
        UserAccountDetailsData userAccountDetailsData = userService.updateUserAccountDetails(updatableUserAccountDetailsData, principal.getName());
        logger.info("-----------------SUCCESSFUL updateUserDetails-----------------");
        return new ResponseEntity <>(userAccountDetailsData, HttpStatus.OK);
    }

    private void logMyUserDetailRequestDto(UserAccountDetailRequestDto userAccountDetailRequestDto) {

        logger.info("username:    {}", userAccountDetailRequestDto.getUsername());
        logger.info("password:    {}", userAccountDetailRequestDto.getPassword());
        logger.info("re-password: {}", userAccountDetailRequestDto.getRepeatPassword());
        logger.info("first name:  {}", userAccountDetailRequestDto.getFirstName());
        logger.info("last name:   {}", userAccountDetailRequestDto.getLastName());
        logger.info("e-mail:      {}", userAccountDetailRequestDto.getEmail());
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

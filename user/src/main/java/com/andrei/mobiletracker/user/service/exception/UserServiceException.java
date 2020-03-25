package com.andrei.mobiletracker.user.service.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UserServiceException extends RuntimeException {

    private HttpStatus httpStatus;
    private UserExceptionType type;

    public UserServiceException(HttpStatus httpStatus, UserExceptionType type) {

        this.httpStatus = httpStatus;
        this.type = type;
    }

    public UserServiceException(String message, HttpStatus httpStatus, UserExceptionType type) {
        super(message);
        this.httpStatus = httpStatus;
        this.type = type;
    }

    public UserServiceException(String message, Throwable cause, HttpStatus httpStatus, UserExceptionType type) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.type = type;
    }
}

package com.andrei.mobiletracker.location.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class LocationServiceException extends ResponseStatusException {

    private LocationExceptionType type;

    public LocationServiceException(String reason, HttpStatus status, LocationExceptionType type) {
        super(status, reason);
        this.type = type;
    }
}
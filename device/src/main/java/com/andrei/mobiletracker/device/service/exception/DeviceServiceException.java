package com.andrei.mobiletracker.device.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class DeviceServiceException extends ResponseStatusException {

    private DeviceExceptionType type;

    public DeviceServiceException(String reason, HttpStatus status, DeviceExceptionType type) {
        super(status, reason);
        this.type = type;
    }
}

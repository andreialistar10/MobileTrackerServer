package com.andrei.mobiletracker.location.service.validator.impl;

import com.andrei.mobiletracker.location.service.exception.LocationExceptionType;
import com.andrei.mobiletracker.location.service.exception.LocationServiceException;
import com.andrei.mobiletracker.location.service.validator.ExistingDeviceValidator;
import com.andrei.mobiletracker.location.util.communication.MicroservicesCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientException;

@Component
public class ExistingDeviceValidatorImpl implements ExistingDeviceValidator {


    @Autowired
    private MicroservicesCaller microservicesCaller;

    @Override
    public void validate(String deviceCode) {

        try {
            microservicesCaller.findDeviceById(deviceCode);
        } catch (WebClientException ex) {
            throw new LocationServiceException("Invalid device code for current user", HttpStatus.BAD_REQUEST, LocationExceptionType.INVALID_DEVICE_CODE);
        }
    }
}

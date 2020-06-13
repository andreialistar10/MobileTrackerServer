package com.andrei.mobiletracker.location.service.validator.impl;

import com.andrei.mobiletracker.location.dto.device.DevicesInformation;
import com.andrei.mobiletracker.location.service.exception.LocationExceptionType;
import com.andrei.mobiletracker.location.service.exception.LocationServiceException;
import com.andrei.mobiletracker.location.service.validator.ExistingDeviceValidator;
import com.andrei.mobiletracker.location.util.communication.MicroservicesCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ExistingDeviceValidatorImpl implements ExistingDeviceValidator {


    @Autowired
    private MicroservicesCaller microservicesCaller;

    @Override
    public void validate(String deviceCode) {

        DevicesInformation devices = microservicesCaller.findAllDevicesForCurrentUser();
        boolean deviceExists = devices.getDevices().stream().anyMatch(decideId -> decideId.equals(deviceCode));
        if (!deviceExists){
            throw new LocationServiceException("Invalid device code for current user", HttpStatus.BAD_REQUEST, LocationExceptionType.INVALID_DEVICE_CODE);
        }
    }
}

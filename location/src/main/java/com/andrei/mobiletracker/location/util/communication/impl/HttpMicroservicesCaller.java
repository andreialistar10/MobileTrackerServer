package com.andrei.mobiletracker.location.util.communication.impl;

import com.andrei.mobiletracker.location.dto.device.DevicesInformation;
import com.andrei.mobiletracker.location.util.communication.MicroservicesCaller;
import com.andrei.mobiletracker.security.microservicesprovider.MicroserviceResponseProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HttpMicroservicesCaller implements MicroservicesCaller {

    @Autowired
    private MicroserviceResponseProvider microserviceResponseProvider;

    @Override
    public DevicesInformation findAllDevicesForCurrentUser() {
        return  microserviceResponseProvider.get("device-service/devices?id-only=true", DevicesInformation.class);
    }
}

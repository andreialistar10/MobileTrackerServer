package com.andrei.mobiletracker.device.security.tokengenerator.impl;

import com.andrei.mobiletracker.device.security.DeviceAuthority;
import com.andrei.mobiletracker.device.security.tokengenerator.DeviceTokenGenerator;
import com.andrei.mobiletracker.security.jwtFilter.authMicroserviceFilters.util.AuthJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DeviceTokenGeneratorImpl implements DeviceTokenGenerator {

    @Autowired
    private AuthJwtUtil authJwtUtil;

    @Override
    public String generateApiToken(String deviceId, DeviceAuthority authority) {

        GrantedAuthority deviceAuthority = new SimpleGrantedAuthority(authority.toString());
        UserDetails deviceCredentials = new User(deviceId, "", Collections.singletonList(deviceAuthority));
        return authJwtUtil.generateToken(deviceCredentials);
    }

    @Override
    public String generateRegisterToken(String deviceId) {

        GrantedAuthority deviceAuthority = new SimpleGrantedAuthority(DeviceAuthority.UNREGISTERED_DEVICE.toString());
        UserDetails deviceCredentials = new User(deviceId, "", Collections.singletonList(deviceAuthority));
        return authJwtUtil.generateUnexpiredToken(deviceCredentials);
    }
}

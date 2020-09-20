package com.andrei.mobiletracker.device.security.tokengenerator;

import com.andrei.mobiletracker.device.security.DeviceAuthority;

public interface DeviceTokenGenerator {

    String generateApiToken(String deviceId, DeviceAuthority authority);

    String generateRegisterToken(String deviceId);

    String generateRefreshToken(String code);
}
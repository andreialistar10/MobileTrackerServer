package com.andrei.mobiletracker.location.util.communication;

import com.andrei.mobiletracker.location.dto.device.DeviceInformation;
import com.andrei.mobiletracker.location.dto.device.DevicesInformation;

public interface MicroservicesCaller {

    DevicesInformation findAllDevicesForCurrentUser();

    DeviceInformation findDeviceById(String deviceCode);
}

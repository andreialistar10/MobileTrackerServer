package com.andrei.mobiletracker.device.facade.device;

import com.andrei.mobiletracker.device.dto.device.DeviceData;
import com.andrei.mobiletracker.device.dto.device.DevicesData;

public interface DeviceFacade {
    DevicesData findAllDevicesByOwnerUsername(String name, boolean idOnly);

    DeviceData findDeviceById(String deviceCode, String username);
}

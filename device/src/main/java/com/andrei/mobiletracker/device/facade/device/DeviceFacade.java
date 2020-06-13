package com.andrei.mobiletracker.device.facade.device;

import com.andrei.mobiletracker.device.dto.device.DevicesData;

public interface DeviceFacade {
    DevicesData findAllDevicesByOwnerUsername(String name, boolean idOnly);
}

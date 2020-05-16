package com.andrei.mobiletracker.device.facade.device;

import com.andrei.mobiletracker.device.dto.ownerdevice.DevicesData;

public interface DeviceFacade {
    DevicesData findAllDevicesByOwnerUsername(String name);
}

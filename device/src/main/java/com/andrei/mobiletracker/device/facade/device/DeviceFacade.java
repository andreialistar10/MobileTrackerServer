package com.andrei.mobiletracker.device.facade.device;

import com.andrei.mobiletracker.device.dto.device.DeviceData;
import com.andrei.mobiletracker.device.dto.device.DevicesData;
import com.andrei.mobiletracker.device.dto.device.UpdateDeviceData;

public interface DeviceFacade {
    DevicesData findAllDevicesByOwnerUsername(String name, boolean idOnly, boolean allowDeleted);

    DeviceData findDeviceById(String deviceCode, String username);

    DeviceData deleteDeviceByIdAndOwnerUsername(String deviceCode, String username);

    DeviceData updateDeviceById(String deviceCode, UpdateDeviceData updateDeviceData, String username);
}

package com.andrei.mobiletracker.device.service.device;

import com.andrei.mobiletracker.device.dto.device.UpdateDeviceData;
import com.andrei.mobiletracker.device.model.Device;

import java.util.List;

public interface DeviceService {

    List<Device> findAllDevicesByOwnerUsername(String username, boolean allowDeleted);

    Device findDeviceByCodeAndOwnerUsername(String deviceCode, String username);

    Device deleteDeviceByCodeAndOwnerUsername(String deviceCode, String username);

    Device updateDevice(String deviceCode, UpdateDeviceData updateDeviceData, String username);
}

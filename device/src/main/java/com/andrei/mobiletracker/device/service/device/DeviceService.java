package com.andrei.mobiletracker.device.service.device;

import com.andrei.mobiletracker.device.model.Device;

import java.util.List;

public interface DeviceService {

    List<Device> findAllDevicesByOwnerUsername(String username);
}

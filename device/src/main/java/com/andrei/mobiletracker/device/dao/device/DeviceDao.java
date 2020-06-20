package com.andrei.mobiletracker.device.dao.device;

import com.andrei.mobiletracker.device.model.Device;

import java.util.List;

public interface DeviceDao {

    Device findOneDeviceByDeviceIdAndOwnerUsername(String id, String ownerUsername);

    List<Device> findAllAvailableDevicesByOwnerUsername(String username);

    Device saveOrUpdateOneDevice(Device device);
}

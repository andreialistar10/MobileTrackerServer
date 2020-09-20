package com.andrei.mobiletracker.device.dao.device;

import com.andrei.mobiletracker.device.model.Device;

import java.util.List;

public interface DeviceDao {

    Device findOneDeviceByDeviceIdAndOwnerUsername(String id, String ownerUsername);

    Device findOneDeviceByDeviceIdAndOwnerUsername(String id, String ownerUsername, boolean allowDeleted);

    List<Device> findAllDevicesByOwnerUsernameAndDeleted(String username, boolean deleted);

    List<Device> findAllDevicesByOwnerUsername(String username);

    Device saveOrUpdateOneDevice(Device device);
}

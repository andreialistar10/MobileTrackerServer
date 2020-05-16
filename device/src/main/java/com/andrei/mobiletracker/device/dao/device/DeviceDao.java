package com.andrei.mobiletracker.device.dao.device;

import com.andrei.mobiletracker.device.model.Device;

public interface DeviceDao {

    Device findOneDeviceByDeviceIdAndOwnerUsername(String id, String ownerUsername);
}

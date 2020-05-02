package com.andrei.mobiletracker.device.dao.deviceconstant;

import com.andrei.mobiletracker.device.model.DeviceConstant;
import com.andrei.mobiletracker.device.model.DeviceConstantName;

public interface DeviceConstantDao {

    DeviceConstant findOneByDeviceConstantName(DeviceConstantName unregisteredDeviceIndex);

    DeviceConstant updateOneDeviceConstant(DeviceConstant deviceConstant);
}

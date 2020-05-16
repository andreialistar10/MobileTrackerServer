package com.andrei.mobiletracker.device.dao.unregistereddevice;

import com.andrei.mobiletracker.device.model.UnregisteredDevice;
import com.andrei.mobiletracker.device.model.UnregisteredDeviceState;

public interface UnregisteredDeviceDao {

    UnregisteredDevice saveOneUnregisteredDevice(UnregisteredDevice unregisteredDevice);

    UnregisteredDevice findOneUnregisteredDeviceByIdAndPasswordAndState(String deviceId, String devicePassword, UnregisteredDeviceState state);

    UnregisteredDevice updateOneUnregisteredDevice(UnregisteredDevice foundUnregisteredDevice);
}

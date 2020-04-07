package com.andrei.mobiletracker.device.facade.unregistereddevice;

import com.andrei.mobiletracker.device.dto.UnregisteredDeviceData;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;

public interface UnregisteredDeviceFacade {

    UnregisteredDevice addUnregisteredDevice(UnregisteredDeviceData unregisteredDeviceData);
}

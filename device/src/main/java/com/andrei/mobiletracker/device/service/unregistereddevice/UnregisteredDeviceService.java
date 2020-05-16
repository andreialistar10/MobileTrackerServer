package com.andrei.mobiletracker.device.service.unregistereddevice;

import com.andrei.mobiletracker.device.model.UnregisteredDevice;

public interface UnregisteredDeviceService {

    UnregisteredDevice tryToPairingUnregisteredDevice(UnregisteredDevice unregisteredDevice);
    UnregisteredDevice saveOneUnregisteredDevice(UnregisteredDevice unregisteredDevice);
}

package com.andrei.mobiletracker.device.service.unregistereddevice;

import com.andrei.mobiletracker.device.model.Device;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;

public interface UnregisteredDeviceService {

    UnregisteredDevice tryToPairingUnregisteredDevice(UnregisteredDevice unregisteredDevice);

    UnregisteredDevice saveOneUnregisteredDevice(UnregisteredDevice unregisteredDevice);

    Device confirmPairing(UnregisteredDevice unregisteredDevice);

    UnregisteredDevice startPairing(String deviceId);
}

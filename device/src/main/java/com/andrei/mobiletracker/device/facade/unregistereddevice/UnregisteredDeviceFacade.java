package com.andrei.mobiletracker.device.facade.unregistereddevice;

import com.andrei.mobiletracker.device.dto.UnregisteredDeviceDataRequest;
import com.andrei.mobiletracker.device.dto.UnregisteredDeviceDataResponse;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;

public interface UnregisteredDeviceFacade {

    UnregisteredDeviceDataResponse addUnregisteredDevice(UnregisteredDeviceDataRequest unregisteredDeviceDataRequest);
}

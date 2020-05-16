package com.andrei.mobiletracker.device.facade.unregistereddevice;

import com.andrei.mobiletracker.device.dto.deviceconnectivity.UnregisteredDeviceCredentialsData;
import com.andrei.mobiletracker.device.dto.unregistereddevice.UnregisteredDeviceDataRequest;
import com.andrei.mobiletracker.device.dto.unregistereddevice.UnregisteredDeviceDataResponse;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;

public interface UnregisteredDeviceFacade {

    UnregisteredDeviceDataResponse addUnregisteredDevice(UnregisteredDeviceDataRequest unregisteredDeviceDataRequest);

    UnregisteredDevice tryToPairing(UnregisteredDeviceCredentialsData unregisteredDeviceCredentialsData);
}

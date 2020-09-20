package com.andrei.mobiletracker.device.facade.unregistereddevice;

import com.andrei.mobiletracker.device.dto.deviceconnectivity.UnregisteredDeviceCredentialsData;
import com.andrei.mobiletracker.device.dto.unregistereddevice.*;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;

public interface UnregisteredDeviceFacade {

    UnregisteredDeviceDataResponse addUnregisteredDevice(UnregisteredDeviceDataRequest unregisteredDeviceDataRequest);

    UnregisteredDevice tryToPairing(UnregisteredDeviceCredentialsData unregisteredDeviceCredentialsData);

    PairingDeviceDataResponse pairing(PairingDeviceDataRequest pairingDeviceDataRequest, String deviceId);

    UnregisteredDevicePasswordData startPairing(String deviceId);

    String setUnpairedDeviceState(String deviceId);
}

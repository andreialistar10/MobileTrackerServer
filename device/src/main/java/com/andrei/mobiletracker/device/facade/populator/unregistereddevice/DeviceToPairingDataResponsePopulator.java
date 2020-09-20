package com.andrei.mobiletracker.device.facade.populator.unregistereddevice;

import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.device.dto.unregistereddevice.PairingDeviceDataResponse;
import com.andrei.mobiletracker.device.model.Device;
import com.andrei.mobiletracker.device.model.DeviceSettings;
import org.springframework.stereotype.Component;

@Component
public class DeviceToPairingDataResponsePopulator implements Populator<PairingDeviceDataResponse, Device> {

    @Override
    public void populate(Device device, PairingDeviceDataResponse pairingDeviceDataResponse) {

        pairingDeviceDataResponse.setDeviceId(device.getCode());
        pairingDeviceDataResponse.setOwnerUsername(device.getOwnerUsername());
        pairingDeviceDataResponse.setRefreshToken(device.getRefreshToken());
        pairingDeviceDataResponse.setTokenApi(device.getTokenApi());
        DeviceSettings deviceSettings = device.getDeviceSettings();
        pairingDeviceDataResponse.setName(deviceSettings.getName());
        pairingDeviceDataResponse.setTimeInterval(deviceSettings.getTimeInterval());
    }
}

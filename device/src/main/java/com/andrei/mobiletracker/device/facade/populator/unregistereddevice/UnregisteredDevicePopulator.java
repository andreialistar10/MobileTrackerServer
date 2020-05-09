package com.andrei.mobiletracker.device.facade.populator.unregistereddevice;

import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.device.dto.UnregisteredDeviceDataResponse;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;
import org.springframework.stereotype.Component;

@Component
public class UnregisteredDevicePopulator implements Populator<UnregisteredDeviceDataResponse, UnregisteredDevice> {

    @Override
    public void populate(UnregisteredDevice unregisteredDevice, UnregisteredDeviceDataResponse unregisteredDeviceDataResponse) {

        unregisteredDeviceDataResponse.setId(unregisteredDevice.getId());
        unregisteredDeviceDataResponse.setName(unregisteredDevice.getName());
    }
}

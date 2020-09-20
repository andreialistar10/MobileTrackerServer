package com.andrei.mobiletracker.device.facade.populator.unregistereddevice;

import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.device.dto.unregistereddevice.UnregisteredDeviceDataRequest;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;
import com.andrei.mobiletracker.device.model.UnregisteredDeviceState;
import org.springframework.stereotype.Component;

@Component
public class UnregisteredDeviceDataRequestToUnregisteredDevicePopulator implements Populator<UnregisteredDevice, UnregisteredDeviceDataRequest> {

    @Override
    public void populate(UnregisteredDeviceDataRequest unregisteredDeviceDataRequest, UnregisteredDevice unregisteredDevice) {

        unregisteredDevice.setName(unregisteredDeviceDataRequest.getName());
        unregisteredDevice.setState(UnregisteredDeviceState.UNPAIRED);
    }
}

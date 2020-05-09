package com.andrei.mobiletracker.device.facade.populator.unregistereddevice;

import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.device.dto.UnregisteredDeviceData;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;
import com.andrei.mobiletracker.device.model.UnregisteredDeviceState;
import org.springframework.stereotype.Component;

@Component
public class UnregisteredDeviceReversePopulator implements Populator<UnregisteredDevice, UnregisteredDeviceData> {

    @Override
    public void populate(UnregisteredDeviceData unregisteredDeviceData, UnregisteredDevice unregisteredDevice) {

        unregisteredDevice.setName(unregisteredDeviceData.getName());
        unregisteredDevice.setState(UnregisteredDeviceState.UNPAIRED);
    }
}

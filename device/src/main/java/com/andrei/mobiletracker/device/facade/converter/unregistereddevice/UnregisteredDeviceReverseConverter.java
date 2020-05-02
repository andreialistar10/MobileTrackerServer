package com.andrei.mobiletracker.device.facade.converter.unregistereddevice;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.device.dto.UnregisteredDeviceData;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnregisteredDeviceReverseConverter implements Converter<UnregisteredDeviceData, UnregisteredDevice> {

    @Autowired
    private Populator<UnregisteredDevice, UnregisteredDeviceData> unregisteredDevicePopulator;

    @Override
    public UnregisteredDevice convert(UnregisteredDeviceData unregisteredDeviceData) {

        UnregisteredDevice unregisteredDevice = new UnregisteredDevice();
        unregisteredDevicePopulator.populate(unregisteredDeviceData, unregisteredDevice);
        return unregisteredDevice;
    }
}

package com.andrei.mobiletracker.device.facade.converter.unregistereddevice;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.device.dto.unregistereddevice.UnregisteredDeviceDataRequest;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnregisteredDeviceFromUnregisteredDeviceDataRequestConverter implements Converter<UnregisteredDeviceDataRequest, UnregisteredDevice> {

    @Autowired
    private Populator<UnregisteredDevice, UnregisteredDeviceDataRequest> unregisteredDevicePopulator;

    @Override
    public UnregisteredDevice convert(UnregisteredDeviceDataRequest unregisteredDeviceDataRequest) {

        UnregisteredDevice unregisteredDevice = new UnregisteredDevice();
        unregisteredDevicePopulator.populate(unregisteredDeviceDataRequest, unregisteredDevice);
        return unregisteredDevice;
    }
}

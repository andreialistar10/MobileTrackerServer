package com.andrei.mobiletracker.device.facade.converter.unregistereddevice;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.device.dto.unregistereddevice.UnregisteredDeviceDataResponse;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnregisteredDeviceDataResponseFromUnregisteredDeviceConverter implements Converter<UnregisteredDevice, UnregisteredDeviceDataResponse> {

    @Autowired
    private Populator<UnregisteredDeviceDataResponse, UnregisteredDevice> populator;

    @Override
    public UnregisteredDeviceDataResponse convert(UnregisteredDevice unregisteredDevice) {

        UnregisteredDeviceDataResponse unregisteredDeviceDataResponse = new UnregisteredDeviceDataResponse();
        populator.populate(unregisteredDevice, unregisteredDeviceDataResponse);
        return unregisteredDeviceDataResponse;
    }
}

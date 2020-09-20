package com.andrei.mobiletracker.device.facade.converter.unregistereddevice;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.device.dto.unregistereddevice.PairingDeviceDataResponse;
import com.andrei.mobiletracker.device.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PairingDeviceDataResponseFromDeviceConverter implements Converter<Device, PairingDeviceDataResponse> {

    @Autowired
    private Populator<PairingDeviceDataResponse, Device> deviceToPairingDeviceDataResponse;

    @Override
    public PairingDeviceDataResponse convert(Device device) {

        PairingDeviceDataResponse pairingDeviceDataResponse = new PairingDeviceDataResponse();
        deviceToPairingDeviceDataResponse.populate(device,pairingDeviceDataResponse);
        return pairingDeviceDataResponse;
    }
}

package com.andrei.mobiletracker.device.facade.converter.unregistereddevice;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.device.dto.deviceconnectivity.UnregisteredDeviceCredentialsData;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnregisteredDeviceFromUnregisteredDeviceCredentialsDataConverter implements Converter<UnregisteredDeviceCredentialsData, UnregisteredDevice> {

    @Autowired
    private Populator<UnregisteredDevice, UnregisteredDeviceCredentialsData> unregisteredDeviceCredentialsDataToUnregisteredDevicePopulator;

    @Override
    public UnregisteredDevice convert(UnregisteredDeviceCredentialsData unregisteredDeviceCredentialsData) {

        UnregisteredDevice unregisteredDevice = new UnregisteredDevice();
        unregisteredDeviceCredentialsDataToUnregisteredDevicePopulator.populate(unregisteredDeviceCredentialsData,unregisteredDevice);
        return unregisteredDevice;
    }
}

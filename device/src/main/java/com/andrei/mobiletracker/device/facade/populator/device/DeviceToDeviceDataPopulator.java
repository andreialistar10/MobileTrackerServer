package com.andrei.mobiletracker.device.facade.populator.device;

import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.device.dto.ownerdevice.DeviceData;
import com.andrei.mobiletracker.device.model.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceToDeviceDataPopulator implements Populator<DeviceData, Device> {

    @Override
    public void populate(Device device, DeviceData deviceData) {

        deviceData.setId(device.getCode());
        deviceData.setName(device.getDeviceSettings().getName());
        deviceData.setRegisteredOn(device.getRegisteredOn());
    }
}



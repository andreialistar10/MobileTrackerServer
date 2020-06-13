package com.andrei.mobiletracker.device.facade.converter.device;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.device.dto.device.DeviceData;
import com.andrei.mobiletracker.device.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceDataFromDeviceConverter implements Converter<Device, DeviceData> {

    @Autowired
    private Populator<DeviceData, Device> deviceToDeviceDataPopulator;

    @Override
    public DeviceData convert(Device device) {

        DeviceData deviceData = new DeviceData();
        deviceToDeviceDataPopulator.populate(device, deviceData);
        return deviceData;
    }
}

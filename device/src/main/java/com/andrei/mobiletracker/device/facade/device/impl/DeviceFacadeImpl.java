package com.andrei.mobiletracker.device.facade.device.impl;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.device.controller.DeviceOwnerController;
import com.andrei.mobiletracker.device.dto.ownerdevice.DeviceData;
import com.andrei.mobiletracker.device.dto.ownerdevice.DevicesData;
import com.andrei.mobiletracker.device.facade.device.DeviceFacade;
import com.andrei.mobiletracker.device.model.Device;
import com.andrei.mobiletracker.device.service.device.DeviceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeviceFacadeImpl implements DeviceFacade {

    private static final Logger logger = LogManager.getLogger(DeviceFacadeImpl.class);

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private Converter<Device, DeviceData> deviceDataFromDeviceConverter;

    @Override
    public DevicesData findAllDevicesByOwnerUsername(String name) {

        logger.info("------------------LOGGING  findAllDevicesByOwnerUsername------------------");
        List<Device> devices = deviceService.findAllDevicesByOwnerUsername(name);
        List<DeviceData> deviceDataList = deviceDataFromDeviceConverter.convertAll(devices);
        logger.info("-----------------SUCCESSFUL findAllDevicesByOwnerUsername-----------------");
        return DevicesData.builder()
                .devices(deviceDataList)
                .build();
    }
}

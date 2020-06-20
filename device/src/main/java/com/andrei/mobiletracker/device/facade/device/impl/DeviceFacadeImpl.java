package com.andrei.mobiletracker.device.facade.device.impl;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.device.dto.device.DeviceData;
import com.andrei.mobiletracker.device.dto.device.DevicesData;
import com.andrei.mobiletracker.device.facade.device.DeviceFacade;
import com.andrei.mobiletracker.device.model.Device;
import com.andrei.mobiletracker.device.service.device.DeviceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeviceFacadeImpl implements DeviceFacade {

    private static final Logger logger = LogManager.getLogger(DeviceFacadeImpl.class);

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private Converter<Device, DeviceData> deviceDataFromDeviceConverter;

    @Override
    public DevicesData findAllDevicesByOwnerUsername(String name, boolean idOnly) {

        logger.info("------------------LOGGING  findAllDevicesByOwnerUsername------------------");
        List<Device> devices = deviceService.findAllDevicesByOwnerUsername(name);
        List<?> deviceInformationDataList = idOnly ? getDeviceIdList(devices) : getDeviceDataList(devices);
        logger.info("-----------------SUCCESSFUL findAllDevicesByOwnerUsername-----------------");
        return DevicesData.builder()
                .devices(deviceInformationDataList)
                .build();
    }

    @Override
    public DeviceData findDeviceById(String deviceCode, String username) {

        logger.info("------------------LOGGING  findDeviceById------------------");
        Device device = deviceService.findDeviceByCodeAndOwnerUsername(deviceCode, username);
        DeviceData deviceData = deviceDataFromDeviceConverter.convert(device);
        logger.info("-----------------SUCCESSFUL findDeviceById-----------------");
        return deviceData;
    }

    private List<DeviceData> getDeviceDataList(List<Device> devices) {

        return deviceDataFromDeviceConverter.convertAll(devices);
    }

    private List<String> getDeviceIdList(List<Device> devices) {

        return devices.stream()
                .map(Device::getCode)
                .collect(Collectors.toList());
    }
}

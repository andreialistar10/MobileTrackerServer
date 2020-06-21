package com.andrei.mobiletracker.device.facade.device.impl;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.device.dto.device.DeviceData;
import com.andrei.mobiletracker.device.dto.device.DevicesData;
import com.andrei.mobiletracker.device.dto.device.UpdateDeviceData;
import com.andrei.mobiletracker.device.facade.device.DeviceFacade;
import com.andrei.mobiletracker.device.message.event.notification.DeviceNotificationType;
import com.andrei.mobiletracker.device.message.event.notification.NotificationData;
import com.andrei.mobiletracker.device.message.event.notification.NotificationEvent;
import com.andrei.mobiletracker.device.message.publisher.MobileTrackerMessagePublisher;
import com.andrei.mobiletracker.device.model.Device;
import com.andrei.mobiletracker.device.model.DeviceSettings;
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

    @Autowired
    private MobileTrackerMessagePublisher<NotificationEvent> notificationEventPublisher;

    @Override
    public DevicesData findAllDevicesByOwnerUsername(String name, boolean idOnly, boolean allowDeleted) {

        logger.info("------------------LOGGING  findAllDevicesByOwnerUsername------------------");
        List<Device> devices = deviceService.findAllDevicesByOwnerUsername(name, allowDeleted);
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

    @Override
    public DeviceData deleteDeviceByIdAndOwnerUsername(String deviceCode, String username) {

        logger.info("------------------LOGGING  deleteDeviceByIdAndOwnerUsername------------------");
        Device device = deviceService.deleteDeviceByCodeAndOwnerUsername(deviceCode, username);
        DeviceData deviceData = deviceDataFromDeviceConverter.convert(device);
        NotificationEvent notificationEvent = createNotificationEvent(DeviceNotificationType.DELETE,device);
        notificationEventPublisher.publish(notificationEvent);
        logger.info("-----------------SUCCESSFUL deleteDeviceByIdAndOwnerUsername-----------------");
        return deviceData;

    }

    @Override
    public DeviceData updateDeviceById(String deviceCode, UpdateDeviceData updateDeviceData, String username) {

        logger.info("------------------LOGGING  updateDeviceById------------------");
        Device device = deviceService.updateDevice(deviceCode, updateDeviceData, username);
        DeviceData deviceData = deviceDataFromDeviceConverter.convert(device);
        NotificationEvent notificationEvent = createNotificationEvent(DeviceNotificationType.UPDATE,device);
        notificationEventPublisher.publish(notificationEvent);
        logger.info("-----------------SUCCESSFUL updateDeviceById-----------------");
        return deviceData;

    }

    private NotificationEvent createNotificationEvent(DeviceNotificationType type, Device device) {

        DeviceSettings deviceSettings = device.getDeviceSettings();
        NotificationData notificationData = NotificationData.builder()
                .name(deviceSettings.getName())
                .build();
        return NotificationEvent.builder()
                .type(type)
                .deviceCode(device.getCode())
                .data(notificationData)
                .build();
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

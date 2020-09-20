package com.andrei.mobiletracker.device.service.device.impl;

import com.andrei.mobiletracker.device.dao.device.DeviceDao;
import com.andrei.mobiletracker.device.dao.devicesettings.DeviceSettingsDao;
import com.andrei.mobiletracker.device.dto.device.UpdateDeviceData;
import com.andrei.mobiletracker.device.model.Device;
import com.andrei.mobiletracker.device.model.DeviceSettings;
import com.andrei.mobiletracker.device.service.device.DeviceService;
import com.andrei.mobiletracker.device.service.exception.DeviceExceptionType;
import com.andrei.mobiletracker.device.service.exception.DeviceServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    private static final Logger logger = LogManager.getLogger(DeviceServiceImpl.class);

    @Override
    @Transactional
    public List<Device> findAllDevicesByOwnerUsername(String username, boolean allowDeleted) {

        logger.info("------------------LOGGING  findAllDevicesByOwnerUsername------------------");
        List<Device> devices;
        if (allowDeleted) {
            devices = deviceDao.findAllDevicesByOwnerUsername(username);
        } else {
            devices = deviceDao.findAllDevicesByOwnerUsernameAndDeleted(username, false);
        }
        devices.forEach(device -> Hibernate.initialize(device.getDeviceSettings()));
        logger.info("-----------------SUCCESSFUL findAllDevicesByOwnerUsername-----------------");
        return devices;
    }

    @Override
    public Device findDeviceByCodeAndOwnerUsername(String deviceCode, String username) {

        logger.info("------------------LOGGING  findDeviceByCodeAndOwnerUsername------------------");
        Device device = deviceDao.findOneDeviceByDeviceIdAndOwnerUsername(deviceCode, username);
        if (device == null) {
            throw new DeviceServiceException("Doesn't exist a device with code: " + deviceCode + " and ownerUsername: " + "username", HttpStatus.NOT_FOUND, DeviceExceptionType.DEVICE_NOT_FOUND);
        }
        logger.info("-----------------SUCCESSFUL findDeviceByCodeAndOwnerUsername-----------------");
        return device;
    }

    @Override
    @Transactional
    public Device deleteDeviceByCodeAndOwnerUsername(String deviceCode, String username) {

        logger.info("------------------LOGGING  deleteDeviceByCodeAndOwnerUsername------------------");
        Device device = this.findDeviceByCodeAndOwnerUsername(deviceCode, username);
        device.setDeleted(true);
        deviceDao.saveOrUpdateOneDevice(device);
        logger.info("-----------------SUCCESSFUL deleteDeviceByCodeAndOwnerUsername-----------------");
        return device;
    }

    @Override
    @Transactional
    public Device updateDevice(String deviceCode, UpdateDeviceData updateDeviceData, String username) {

        logger.info("------------------LOGGING  updateDevice------------------");
        Device device = this.findDeviceByCodeAndOwnerUsername(deviceCode, username);
        DeviceSettings deviceSettings = device.getDeviceSettings();
        deviceSettings.setName(updateDeviceData.getName());
        logger.info("-----------------SUCCESSFUL updateDevice-----------------");
        return device;    }
}

package com.andrei.mobiletracker.device.service.device.impl;

import com.andrei.mobiletracker.device.dao.device.DeviceDao;
import com.andrei.mobiletracker.device.model.Device;
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

    @Transactional(readOnly = true)
    @Override
    public List<Device> findAllDevicesByOwnerUsername(String username) {

        logger.info("------------------LOGGING  findAllDevicesByOwnerUsername------------------");
        List<Device> devices = deviceDao.findAllAvailableDevicesByOwnerUsername(username);
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
}

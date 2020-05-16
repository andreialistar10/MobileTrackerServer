package com.andrei.mobiletracker.device.service.device.impl;

import com.andrei.mobiletracker.device.dao.device.DeviceDao;
import com.andrei.mobiletracker.device.model.Device;
import com.andrei.mobiletracker.device.service.device.DeviceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    private static final Logger logger = LogManager.getLogger(DeviceServiceImpl.class);


    @Transactional(readOnly = true)
    @Override
    public List<Device> findAllDevicesByOwnerUsername(String username) {

        logger.info("------------------LOGGING  findAllDevicesByOwnerUsername------------------");
        List<Device> devices = deviceDao.findAllAvailableDevicesByOwnerUsernameAndDeleted(username);
        devices.forEach(device -> Hibernate.initialize(device.getDeviceSetting()));
        logger.info("-----------------SUCCESSFUL findAllDevicesByOwnerUsername-----------------");
        return devices;
    }
}

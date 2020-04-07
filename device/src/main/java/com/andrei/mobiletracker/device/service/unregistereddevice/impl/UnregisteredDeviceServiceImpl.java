package com.andrei.mobiletracker.device.service.unregistereddevice.impl;

import com.andrei.mobiletracker.device.dao.deviceconstant.DeviceConstantDao;
import com.andrei.mobiletracker.device.dao.unregistereddevice.UnregisteredDeviceDao;
import com.andrei.mobiletracker.device.model.DeviceConstant;
import com.andrei.mobiletracker.device.model.DeviceConstantName;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;
import com.andrei.mobiletracker.device.service.unregistereddevice.UnregisteredDeviceService;
import com.andrei.mobiletracker.device.service.util.UnregisteredDeviceCodeGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnregisteredDeviceServiceImpl implements UnregisteredDeviceService {

    private static final Logger logger = LogManager.getLogger(UnregisteredDeviceServiceImpl.class);

    @Autowired
    private UnregisteredDeviceDao unregisteredDeviceDao;

    @Autowired
    private UnregisteredDeviceCodeGenerator unregisteredDeviceCodeGenerator;

    @Transactional
    @Override
    public UnregisteredDevice saveOneUnregisteredDevice(UnregisteredDevice unregisteredDevice) {

        logger.info("------------------LOGGING  saveOneUnregisteredDevice------------------");
        String code = unregisteredDeviceCodeGenerator.generate(unregisteredDevice.getName());
        unregisteredDevice.setId(code);
        unregisteredDevice = unregisteredDeviceDao.saveOneUnregisteredDevice(unregisteredDevice);
        logger.info("-----------------SUCCESSFUL saveOneUnregisteredDevice-----------------");
        return unregisteredDevice;
    }
}

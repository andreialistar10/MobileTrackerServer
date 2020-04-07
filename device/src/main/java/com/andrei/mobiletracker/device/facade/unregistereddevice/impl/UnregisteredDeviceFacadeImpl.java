package com.andrei.mobiletracker.device.facade.unregistereddevice.impl;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.device.dto.UnregisteredDeviceData;
import com.andrei.mobiletracker.device.facade.unregistereddevice.UnregisteredDeviceFacade;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;
import com.andrei.mobiletracker.device.service.unregistereddevice.UnregisteredDeviceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnregisteredDeviceFacadeImpl implements UnregisteredDeviceFacade {

    private static final Logger logger = LogManager.getLogger(UnregisteredDeviceFacadeImpl.class);

    @Autowired
    private Converter<UnregisteredDeviceData, UnregisteredDevice> unregisteredDeviceReverseConverter;

    @Autowired
    private UnregisteredDeviceService unregisteredDeviceService;

    @Override
    public UnregisteredDevice addUnregisteredDevice(UnregisteredDeviceData unregisteredDeviceData) {

        logger.info("------------------LOGGING  addUnregisteredDevice------------------");
        UnregisteredDevice unregisteredDevice = unregisteredDeviceReverseConverter.convert(unregisteredDeviceData);
        unregisteredDevice = unregisteredDeviceService.saveOneUnregisteredDevice(unregisteredDevice);
        logger.info("-----------------SUCCESSFUL addUnregisteredDevice-----------------");
        return unregisteredDevice;
    }
}

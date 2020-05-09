package com.andrei.mobiletracker.device.facade.unregistereddevice.impl;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.device.dto.UnregisteredDeviceDataRequest;
import com.andrei.mobiletracker.device.dto.UnregisteredDeviceDataResponse;
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
    private Converter<UnregisteredDeviceDataRequest, UnregisteredDevice> unregisteredDeviceReverseConverter;

    @Autowired
    private Converter<UnregisteredDevice, UnregisteredDeviceDataResponse> unregisteredDeviceConverter;

    @Autowired
    private UnregisteredDeviceService unregisteredDeviceService;

    @Override
    public UnregisteredDeviceDataResponse addUnregisteredDevice(UnregisteredDeviceDataRequest unregisteredDeviceDataRequest) {

        logger.info("------------------LOGGING  addUnregisteredDevice------------------");
        UnregisteredDevice unregisteredDevice = unregisteredDeviceReverseConverter.convert(unregisteredDeviceDataRequest);
        unregisteredDevice = unregisteredDeviceService.saveOneUnregisteredDevice(unregisteredDevice);
        logger.info("-----------------SUCCESSFUL addUnregisteredDevice-----------------");
        return unregisteredDeviceConverter.convert(unregisteredDevice);
    }
}

package com.andrei.mobiletracker.device.facade.unregistereddevice.impl;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.device.dto.deviceconnectivity.UnregisteredDeviceCredentialsData;
import com.andrei.mobiletracker.device.dto.unregistereddevice.*;
import com.andrei.mobiletracker.device.facade.unregistereddevice.UnregisteredDeviceFacade;
import com.andrei.mobiletracker.device.model.Device;
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
    private Converter<UnregisteredDeviceCredentialsData, UnregisteredDevice> unregisteredDeviceFromUnregisteredDeviceCredentialsDataConverter;

    @Autowired
    private Converter<Device,PairingDeviceDataResponse > pairingDeviceDataResponseFromDeviceConverter;

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

    @Override
    public UnregisteredDevice tryToPairing(UnregisteredDeviceCredentialsData unregisteredDeviceCredentialsData) {

        logger.info("------------------LOGGING  tryToPairing------------------");
        UnregisteredDevice unregisteredDevice = unregisteredDeviceFromUnregisteredDeviceCredentialsDataConverter.convert(unregisteredDeviceCredentialsData);
        UnregisteredDevice storedUnregisteredDevice = unregisteredDeviceService.tryToPairingUnregisteredDevice(unregisteredDevice);
        logger.info("-----------------SUCCESSFUL tryToPairing-----------------");
        return storedUnregisteredDevice;
    }

    @Override
    public PairingDeviceDataResponse pairing(PairingDeviceDataRequest pairingDeviceDataRequest, String deviceId) {

        logger.info("------------------LOGGING  pairing------------------");

        UnregisteredDevice unregisteredDevice = UnregisteredDevice.builder()
                .id(deviceId)
                .tryingToPairingUsername(pairingDeviceDataRequest.getOwnerUsername())
                .build();
        Device savedDevice = unregisteredDeviceService.confirmPairing(unregisteredDevice);
        logger.info("-----------------SUCCESSFUL pairing-----------------");
        return pairingDeviceDataResponseFromDeviceConverter.convert(savedDevice);
    }

    @Override
    public UnregisteredDevicePasswordData startPairing(String deviceId) {

        logger.info("------------------LOGGING  startPairing------------------");
        UnregisteredDevice unregisteredDevice = unregisteredDeviceService.startPairing(deviceId);
        logger.info("-----------------SUCCESSFUL startPairing-----------------");
        return UnregisteredDevicePasswordData.builder()
                .password(unregisteredDevice.getPassword())
                .build();
    }

    @Override
    public void setUnpairedDeviceState(String deviceId) {

        logger.info("------------------LOGGING  setUnpairedDeviceState------------------");
        unregisteredDeviceService.deviceDisconnect(deviceId);
        logger.info("-----------------SUCCESSFUL setUnpairedDeviceState-----------------");
    }
}

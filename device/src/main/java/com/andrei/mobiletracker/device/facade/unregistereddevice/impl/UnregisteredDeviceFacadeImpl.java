package com.andrei.mobiletracker.device.facade.unregistereddevice.impl;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.device.dto.deviceconnectivity.UnregisteredDeviceCredentialsData;
import com.andrei.mobiletracker.device.dto.unregistereddevice.*;
import com.andrei.mobiletracker.device.facade.unregistereddevice.UnregisteredDeviceFacade;
import com.andrei.mobiletracker.device.message.event.pairing.PairingEvent;
import com.andrei.mobiletracker.device.message.publisher.MobileTrackerMessagePublisher;
import com.andrei.mobiletracker.device.model.Device;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;
import com.andrei.mobiletracker.device.model.UnregisteredDeviceState;
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

    @Autowired
    private MobileTrackerMessagePublisher<PairingEvent> pairingPublisher;

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
        PairingEvent pairingEvent = createPairingEvent(storedUnregisteredDevice);
        pairingPublisher.publish(pairingEvent);
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
        PairingEvent pairingEvent = generatePairingEvent(savedDevice, deviceId);
        pairingPublisher.publish(pairingEvent);
        logger.info("-----------------SUCCESSFUL pairing-----------------");
        return pairingDeviceDataResponseFromDeviceConverter.convert(savedDevice);
    }

    private PairingEvent generatePairingEvent(Device savedDevice, String deviceId) {

        PairingEvent pairedDeviceEvent = new PairingEvent();
        pairedDeviceEvent.setDeviceCode(deviceId);
        pairedDeviceEvent.setDeviceName(savedDevice.getDeviceSettings().getName());
        pairedDeviceEvent.setRegisteredOn(savedDevice.getRegisteredOn());
        pairedDeviceEvent.setState(UnregisteredDeviceState.PAIRED);
        pairedDeviceEvent.setOwnerUsername(savedDevice.getOwnerUsername());
        pairedDeviceEvent.setDeviceCodeAfterPairing(savedDevice.getCode());
        return pairedDeviceEvent;
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
    public String setUnpairedDeviceState(String deviceId) {

        logger.info("------------------LOGGING  setUnpairedDeviceState------------------");
        String tryingToPairingUsername = unregisteredDeviceService.deviceDisconnect(deviceId);
        logger.info("-----------------SUCCESSFUL setUnpairedDeviceState-----------------");
        return tryingToPairingUsername;
    }

    private PairingEvent createPairingEvent(UnregisteredDevice unregisteredDevice) {

        return PairingEvent.builder()
                .deviceCode(unregisteredDevice.getId())
                .ownerUsername(unregisteredDevice.getTryingToPairingUsername())
                .state(unregisteredDevice.getState())
                .build();
    }
}

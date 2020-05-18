package com.andrei.mobiletracker.device.service.unregistereddevice.impl;

import com.andrei.mobiletracker.device.dao.device.DeviceDao;
import com.andrei.mobiletracker.device.dao.devicesettings.DeviceSettingsDao;
import com.andrei.mobiletracker.device.dao.unregistereddevice.UnregisteredDeviceDao;
import com.andrei.mobiletracker.device.message.event.pairing.PairingEvent;
import com.andrei.mobiletracker.device.message.publisher.MobileTrackerMessagePublisher;
import com.andrei.mobiletracker.device.model.Device;
import com.andrei.mobiletracker.device.model.DeviceSettings;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;
import com.andrei.mobiletracker.device.model.UnregisteredDeviceState;
import com.andrei.mobiletracker.device.security.DeviceAuthority;
import com.andrei.mobiletracker.device.security.tokengenerator.DeviceTokenGenerator;
import com.andrei.mobiletracker.device.service.exception.DeviceExceptionType;
import com.andrei.mobiletracker.device.service.exception.DeviceServiceException;
import com.andrei.mobiletracker.device.service.unregistereddevice.UnregisteredDeviceService;
import com.andrei.mobiletracker.device.service.util.UnregisteredDeviceCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnregisteredDeviceServiceImpl implements UnregisteredDeviceService {

    private static final Logger logger = LogManager.getLogger(UnregisteredDeviceServiceImpl.class);

    private static final int DEFAULT_INTERVAL = 60000;

    private static final int PASSWORD_LENGTH = 8;

    @Autowired
    private UnregisteredDeviceDao unregisteredDeviceDao;

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private DeviceSettingsDao deviceSettingsDao;

    @Autowired
    private UnregisteredDeviceCodeGenerator unregisteredDeviceCodeGenerator;

    @Autowired
    private MobileTrackerMessagePublisher<PairingEvent> pairingPublisher;

    @Autowired
    private DeviceTokenGenerator tokenGenerator;

    @Transactional
    @Override
    public UnregisteredDevice saveOneUnregisteredDevice(UnregisteredDevice unregisteredDevice) {

        logger.info("------------------LOGGING  saveOneUnregisteredDevice------------------");
        String code = unregisteredDeviceCodeGenerator.generate(unregisteredDevice.getName());
        String token = tokenGenerator.generateRegisterToken(code);

        unregisteredDevice.setId(code);
        unregisteredDevice.setIdAfterPairing(code);
        unregisteredDevice.setToken(token);
        unregisteredDevice = unregisteredDeviceDao.saveOneUnregisteredDevice(unregisteredDevice);
        logger.info("-----------------SUCCESSFUL saveOneUnregisteredDevice-----------------");
        return unregisteredDevice;
    }

    @Transactional
    @Override
    public Device confirmPairing(UnregisteredDevice unregisteredDevice) {

        logger.info("------------------LOGGING  confirmPairing------------------");
        UnregisteredDevice foundUnregisteredDevice = findCompleteAndValidateExistingUnregisteredDeviceOnConfirmPairing(unregisteredDevice);
        Device device = convertUnregisteredDeviceToDevice(foundUnregisteredDevice);
        String apiToken = tokenGenerator.generateApiToken(device.getCode(), DeviceAuthority.REGISTERED_DEVICE);
        String refreshToken = tokenGenerator.generateRefreshToken(device.getCode());
        device.setTokenApi(apiToken);
        device.setRefreshToken(refreshToken);
        device = deviceDao.saveOrUpdateOneDevice(device);
        unregisteredDeviceDao.deleteOneUnregisteredDevice(unregisteredDevice.getId());

        logger.info("-----------------SUCCESSFUL confirmPairing-----------------");
        return device;
    }

    @Transactional
    @Override
    public UnregisteredDevice startPairing(String deviceId) {

        logger.info("------------------LOGGING  startPairing------------------");
        UnregisteredDevice unregisteredDevice = unregisteredDeviceDao.findOneUnregisteredDeviceById(deviceId);
        if (unregisteredDevice == null){
            throw new DeviceServiceException("Does not exist a device with id: " + deviceId, HttpStatus.BAD_REQUEST, DeviceExceptionType.INVALID_DATA);
        }
        if (unregisteredDevice.getState() != UnregisteredDeviceState.UNPAIRED){
            throw new DeviceServiceException("Device: " + deviceId + " already starts pairing!", HttpStatus.BAD_REQUEST, DeviceExceptionType.INVALID_DATA);
        }
        String password = RandomStringUtils.random(PASSWORD_LENGTH, true, true);
        unregisteredDevice.setState(UnregisteredDeviceState.PAIRING);
        unregisteredDevice.setPassword(password);
//        unregisteredDeviceDao.saveOneUnregisteredDevice(unregisteredDevice);
        logger.info("-----------------SUCCESSFUL startPairing-----------------");
        return unregisteredDevice;
    }

    private UnregisteredDevice findCompleteAndValidateExistingUnregisteredDeviceOnConfirmPairing(UnregisteredDevice unregisteredDevice) {

        UnregisteredDevice foundUnregisteredDevice = unregisteredDeviceDao.findOneUnregisteredDeviceById(unregisteredDevice.getId());
        if (foundUnregisteredDevice == null) {
            throw new DeviceServiceException("Does not exist a device with id: " + unregisteredDevice.getId(), HttpStatus.BAD_REQUEST, DeviceExceptionType.INVALID_DATA);
        }
        String tryingToPairUsername = foundUnregisteredDevice.getTryingToPairingUsername();
        if (foundUnregisteredDevice.getState() != UnregisteredDeviceState.PAIRED || !unregisteredDevice.getTryingToPairingUsername().equals(tryingToPairUsername)) {
            throw new DeviceServiceException("Device is not on PAIRED state or incorrect tryingToPairingUsername", HttpStatus.BAD_REQUEST, DeviceExceptionType.INVALID_DATA);
        }
        return foundUnregisteredDevice;
    }

    private Device convertUnregisteredDeviceToDevice(UnregisteredDevice unregisteredDevice) {

        DeviceSettings deviceSettings = DeviceSettings.builder()
                .name(unregisteredDevice.getName())
                .timeInterval(DEFAULT_INTERVAL)
                .build();
        deviceSettingsDao.saveOneDeviceSettings(deviceSettings);
        return Device.builder()
                .code(unregisteredDevice.getIdAfterPairing())
                .deleted(false)
                .ownerUsername(unregisteredDevice.getTryingToPairingUsername())
                .deviceSettings(deviceSettings)
                .build();
    }


    @Transactional
    @Override
    public UnregisteredDevice tryToPairingUnregisteredDevice(UnregisteredDevice unregisteredDevice) {

        logger.info("------------------LOGGING  pairUnregisteredDevice------------------");
        logger.info("Username: {}", unregisteredDevice.getTryingToPairingUsername());

        verifyTargetDeviceAlreadyExists(unregisteredDevice);
        UnregisteredDevice foundUnregisteredDevice = tryToPairingOneUnregisteredDevice(unregisteredDevice);
        PairingEvent pairingEvent = createPairingEvent(unregisteredDevice);
        pairingPublisher.publish(pairingEvent);
        logger.info("-----------------SUCCESSFUL pairUnregisteredDevice-----------------");
        return foundUnregisteredDevice;
    }

    private PairingEvent createPairingEvent(UnregisteredDevice unregisteredDevice) {

        return PairingEvent.builder()
                .deviceCode(unregisteredDevice.getId())
                .ownerUsername(unregisteredDevice.getTryingToPairingUsername())
                .state(unregisteredDevice.getState())
                .build();
    }

    private UnregisteredDevice tryToPairingOneUnregisteredDevice(UnregisteredDevice unregisteredDevice) {

        String deviceId = unregisteredDevice.getId();
        String devicePassword = unregisteredDevice.getPassword();
        UnregisteredDevice foundUnregisteredDevice = unregisteredDeviceDao.findOneUnregisteredDeviceByIdAndPasswordAndState(deviceId, devicePassword, UnregisteredDeviceState.PAIRING);
        if (foundUnregisteredDevice == null) {
            throw new DeviceServiceException(
                    "Credentials don't match any unpaired devices set to visible or someone else has already tried to pair your phone. Make sure your phone is set visible, otherwise you cannot pair it.",
                    HttpStatus.BAD_REQUEST,
                    DeviceExceptionType.INVALID_DATA
            );
        }
        foundUnregisteredDevice.setState(UnregisteredDeviceState.TRYING_TO_PAIR);
        foundUnregisteredDevice.setTryingToPairingUsername(unregisteredDevice.getTryingToPairingUsername());
        foundUnregisteredDevice.setIdAfterPairing(unregisteredDevice.getIdAfterPairing());
        foundUnregisteredDevice = unregisteredDeviceDao.updateOneUnregisteredDevice(foundUnregisteredDevice);
        return foundUnregisteredDevice;
    }

    private void verifyTargetDeviceAlreadyExists(UnregisteredDevice unregisteredDevice) {

        String currentId = unregisteredDevice.getId();
        String targetId = unregisteredDevice.getId();
        if (currentId.equals(targetId)) {
            return;
        }
        Device foundDevice = deviceDao.findOneDeviceByDeviceIdAndOwnerUsername(targetId, unregisteredDevice.getTryingToPairingUsername());
        if (foundDevice == null) {
            throw new DeviceServiceException(
                    "The old device you want to replace by pairing with the new device does not exist.",
                    HttpStatus.BAD_REQUEST,
                    DeviceExceptionType.INVALID_DATA
            );
        }
    }
}

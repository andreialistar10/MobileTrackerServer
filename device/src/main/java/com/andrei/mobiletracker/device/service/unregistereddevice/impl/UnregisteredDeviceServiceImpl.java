package com.andrei.mobiletracker.device.service.unregistereddevice.impl;

import com.andrei.mobiletracker.device.dao.device.DeviceDao;
import com.andrei.mobiletracker.device.dao.devicesettings.DeviceSettingsDao;
import com.andrei.mobiletracker.device.dao.unregistereddevice.UnregisteredDeviceDao;
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

    private static final int PASSWORD_LENGTH = 6;

    @Autowired
    private UnregisteredDeviceDao unregisteredDeviceDao;

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private DeviceSettingsDao deviceSettingsDao;

    @Autowired
    private UnregisteredDeviceCodeGenerator unregisteredDeviceCodeGenerator;

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
        Device device = createDeviceByUnregisteredDeviceInformation(foundUnregisteredDevice);
        String apiToken = tokenGenerator.generateApiToken(device.getCode(), DeviceAuthority.REGISTERED_DEVICE);
        String refreshToken = tokenGenerator.generateRefreshToken(device.getCode());
        device.setTokenApi(apiToken);
        device.setRefreshToken(refreshToken);
        device.setRegisteredOn(System.currentTimeMillis());
        unregisteredDeviceDao.deleteOneUnregisteredDevice(unregisteredDevice.getId());
        deviceDao.saveOrUpdateOneDevice(device);
        logger.info("-----------------SUCCESSFUL confirmPairing-----------------");
        return device;
    }

    @Transactional
    @Override
    public UnregisteredDevice startPairing(String deviceId) {

        logger.info("------------------LOGGING  startPairing------------------");
        UnregisteredDevice unregisteredDevice = unregisteredDeviceDao.findOneUnregisteredDeviceById(deviceId);
        if (unregisteredDevice == null) {
            throw new DeviceServiceException("Does not exist a device with id: " + deviceId, HttpStatus.BAD_REQUEST, DeviceExceptionType.INVALID_DATA);
        }
        if (unregisteredDevice.getState() != UnregisteredDeviceState.UNPAIRED) {
            throw new DeviceServiceException("Device: " + deviceId + " already starts pairing!", HttpStatus.BAD_REQUEST, DeviceExceptionType.INVALID_DATA);
        }
        String password = RandomStringUtils.random(PASSWORD_LENGTH, false, true);
        unregisteredDevice.setState(UnregisteredDeviceState.PAIRING);
        unregisteredDevice.setPassword(password);
        logger.info("-----------------SUCCESSFUL startPairing-----------------");
        return unregisteredDevice;
    }

    @Transactional
    @Override
    public UnregisteredDevice tryToPairingUnregisteredDevice(UnregisteredDevice unregisteredDevice) {

        logger.info("------------------LOGGING  pairUnregisteredDevice------------------");
        logger.info("Username: {}", unregisteredDevice.getTryingToPairingUsername());

        verifyTargetDeviceAlreadyExists(unregisteredDevice);
        UnregisteredDevice foundUnregisteredDevice = tryToPairingOneUnregisteredDevice(unregisteredDevice);
        logger.info("-----------------SUCCESSFUL pairUnregisteredDevice-----------------");
        return foundUnregisteredDevice;
    }

    @Override
    @Transactional
    public String deviceDisconnect(String deviceId) {

        logger.info("------------------LOGGING  deviceDisconnect------------------");
        UnregisteredDevice unregisteredDevice = unregisteredDeviceDao.findOneUnregisteredDeviceById(deviceId);
        if (unregisteredDevice == null) {
            throw new DeviceServiceException("Invalid device code!", HttpStatus.BAD_REQUEST, DeviceExceptionType.INVALID_DATA);
        }
        String tryingToPairingUsername = unregisteredDevice.getTryingToPairingUsername();
        unregisteredDevice.setState(UnregisteredDeviceState.UNPAIRED);
        unregisteredDevice.setTryingToPairingUsername(null);
        logger.info("-----------------SUCCESSFUL deviceDisconnect-----------------");
        return tryingToPairingUsername;
    }

    private UnregisteredDevice findCompleteAndValidateExistingUnregisteredDeviceOnConfirmPairing(UnregisteredDevice unregisteredDevice) {

        UnregisteredDevice foundUnregisteredDevice = unregisteredDeviceDao.findOneUnregisteredDeviceById(unregisteredDevice.getId());
        if (foundUnregisteredDevice == null) {
            throw new DeviceServiceException("Does not exist a device with id: " + unregisteredDevice.getId(), HttpStatus.BAD_REQUEST, DeviceExceptionType.INVALID_DATA);
        }
        String tryingToPairUsername = foundUnregisteredDevice.getTryingToPairingUsername();
        if (foundUnregisteredDevice.getState() != UnregisteredDeviceState.TRYING_TO_PAIR || !unregisteredDevice.getTryingToPairingUsername().equals(tryingToPairUsername)) {
            throw new DeviceServiceException("Device is not on PAIRED state or incorrect tryingToPairingUsername", HttpStatus.BAD_REQUEST, DeviceExceptionType.INVALID_DATA);
        }
        return foundUnregisteredDevice;
    }

    private Device createDeviceByUnregisteredDeviceInformation(UnregisteredDevice unregisteredDevice) {

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

    private UnregisteredDevice tryToPairingOneUnregisteredDevice(UnregisteredDevice unregisteredDevice) {

        String deviceId = unregisteredDevice.getId();
        String devicePassword = unregisteredDevice.getPassword();
        UnregisteredDevice foundUnregisteredDevice = unregisteredDeviceDao.findOneUnregisteredDeviceByIdAndPasswordAndState(deviceId, devicePassword, UnregisteredDeviceState.PAIRING);
        if (foundUnregisteredDevice == null) {
            throw new DeviceServiceException(
                    "Credentials do not match any unpaired device or someone else has already tried to pair your smartphone before you. Make sure your smartphone is set to visible, otherwise you can't pair it.", HttpStatus.BAD_REQUEST,
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
        String targetId = unregisteredDevice.getIdAfterPairing();
        if (currentId.equals(targetId)) {
            return;
        }
        Device foundDevice = deviceDao.findOneDeviceByDeviceIdAndOwnerUsername(targetId, unregisteredDevice.getTryingToPairingUsername(), true);
        if (foundDevice == null) {
            throw new DeviceServiceException(
                    "The old device you want to replace by pairing with the new device does not exist.",
                    HttpStatus.BAD_REQUEST,
                    DeviceExceptionType.INVALID_DATA
            );
        }
    }
}

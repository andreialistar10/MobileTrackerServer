package com.andrei.mobiletracker.device.controller;

import com.andrei.mobiletracker.device.config.WebSocketConfig;
import com.andrei.mobiletracker.device.controller.exception.DeviceConnectivityException;
import com.andrei.mobiletracker.device.dto.deviceconnectivity.UnregisteredDeviceCredentialsData;
import com.andrei.mobiletracker.device.facade.unregistereddevice.UnregisteredDeviceFacade;
import com.andrei.mobiletracker.device.service.exception.DeviceServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;

@Controller
public class DeviceConnectivityController {

    private static final Logger logger = LogManager.getLogger(DeviceConnectivityController.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UnregisteredDeviceFacade unregisteredDeviceFacade;

    @MessageMapping("/pairing/{deviceId}/user/{username}")
//    @SendTo("/devices/pairing/{deviceId}")
    public void pairingDevice(UnregisteredDeviceCredentialsData deviceCredentialsData, @DestinationVariable String deviceId, @DestinationVariable String username) {

        logger.info("------------------LOGGING  pairingDevice------------------");
        logger.info("ID: {}", deviceCredentialsData.getId());

        try {
            deviceCredentialsData.setTryingToPairUsername(username);
            unregisteredDeviceFacade.tryToPairing(deviceCredentialsData);
        } catch (DeviceServiceException exception) {
            String topic = String.format("%s/pairing/%s/user/%s", WebSocketConfig.ERROR_TOPIC, deviceId, username);
            throw new DeviceConnectivityException(exception.getMessage(), topic);
        }

        logger.info("-----------------SUCCESSFUL pairingDevice-----------------");
//        throw new DeviceConnectivityException("Nu e bun", topic);
//        PendingPairingData pendingPairingData = PendingPairingData.builder().
//                username(username).
//                build();
//        return pendingPairingData;
    }

    @MessageExceptionHandler({DeviceConnectivityException.class})
    public void handleException(DeviceConnectivityException exception) {

        logger.error("------------------LOGGING  handleException------------------");
        logger.error("Topic:   {}", exception.getTopic());
        logger.error("Message: {}", exception.getMessage());
        logger.error("-----------------SUCCESSFUL handleException-----------------");

        TextMessage message = new TextMessage(exception.getMessage());
        simpMessagingTemplate.convertAndSend(exception.getTopic(), message);
    }
}

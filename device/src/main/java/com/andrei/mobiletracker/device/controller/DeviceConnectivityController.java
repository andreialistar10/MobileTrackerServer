package com.andrei.mobiletracker.device.controller;

import com.andrei.mobiletracker.device.config.StompConfig;
import com.andrei.mobiletracker.device.controller.exception.DeviceConnectivityException;
import com.andrei.mobiletracker.device.dto.deviceconnectivity.UnregisteredDeviceCredentialsData;
import com.andrei.mobiletracker.device.facade.unregistereddevice.UnregisteredDeviceFacade;
import com.andrei.mobiletracker.device.message.event.pairing.PairingEvent;
import com.andrei.mobiletracker.device.message.publisher.MobileTrackerMessagePublisher;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;
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

    @Autowired
    private MobileTrackerMessagePublisher<PairingEvent> pairingPublisher;

    @MessageMapping("/pairing/{deviceId}/user/{username}")
    public void pairingDevice(UnregisteredDeviceCredentialsData deviceCredentialsData, @DestinationVariable String deviceId, @DestinationVariable String username) {

        logger.info("------------------LOGGING  pairingDevice------------------");
        logger.info("ID: {}", deviceCredentialsData.getId());

        try {
            deviceCredentialsData.setTryingToPairUsername(username);
            UnregisteredDevice unregisteredDevice = unregisteredDeviceFacade.tryToPairing(deviceCredentialsData);
            PairingEvent pairingEvent = createPairingEvent(unregisteredDevice);
            pairingPublisher.publish(pairingEvent);
        } catch (DeviceServiceException exception) {
            String topic = String.format("/pairing-device/%s/user/%s", deviceId, username);
            throw new DeviceConnectivityException(exception.getMessage(), topic);
        }

        logger.info("-----------------SUCCESSFUL pairingDevice-----------------");
    }

    private PairingEvent createPairingEvent(UnregisteredDevice unregisteredDevice) {

        return PairingEvent.builder()
                .deviceCode(unregisteredDevice.getId())
                .ownerUsername(unregisteredDevice.getTryingToPairingUsername())
                .state(unregisteredDevice.getState())
                .build();
    }

    @MessageExceptionHandler({DeviceConnectivityException.class})
    public void handleException(DeviceConnectivityException exception) {

        logger.error("------------------LOGGING  handleException------------------");
        logger.error("Topic:   {}", exception.getTopic());
        logger.error("Message: {}", exception.getMessage());
        logger.error("-----------------SUCCESSFUL handleException-----------------");

        TextMessage message = new TextMessage(exception.getMessage());
        String topic = StompConfig.ERROR_TOPIC + exception.getTopic();
        simpMessagingTemplate.convertAndSend(topic, message);
    }
}

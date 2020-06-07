package com.andrei.mobiletracker.device.stomp.handler;

import com.andrei.mobiletracker.device.facade.unregistereddevice.UnregisteredDeviceFacade;
import com.andrei.mobiletracker.device.message.event.pairing.PairingEvent;
import com.andrei.mobiletracker.device.message.publisher.MobileTrackerMessagePublisher;
import com.andrei.mobiletracker.device.model.UnregisteredDeviceState;
import com.andrei.mobiletracker.device.security.DeviceAuthority;
import com.andrei.mobiletracker.device.service.exception.DeviceServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class StompSessionDisconnectApplicationListener implements ApplicationListener<SessionDisconnectEvent> {

    private static final Logger logger = LogManager.getLogger(StompSessionDisconnectApplicationListener.class);

    @Autowired
    private UnregisteredDeviceFacade unregisteredDeviceFacade;

    @Autowired
    private MobileTrackerMessagePublisher<PairingEvent> pairingPublisher;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {

        logger.info("------------------LOGGING  SESSION DISCONNECT------------------");
        try {
            StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
            Authentication authentication = (Authentication) stompHeaderAccessor.getUser();
            if (authentication != null) {
                logger.info("AUTHORITIES: {}", authentication.getAuthorities());
                logger.info("ID:          {}", authentication.getName());
                handleClientDisconnect(authentication);
            }
            logger.info("-----------------SUCCESSFUL SESSION DISCONNECT-----------------");
        } catch (DeviceServiceException exception) {
            logger.error("ERROR ON SESSION DISCONNECT: {}", exception.getMessage());
        }
    }

    private void handleClientDisconnect(Authentication authentication) {

        final boolean isUnregisteredDevice = authentication.getAuthorities()
                .stream().map(Object::toString)
                .anyMatch(authority -> authority.equals(DeviceAuthority.UNREGISTERED_DEVICE.toString()));
        if (isUnregisteredDevice) {
            String tryingToPairingUsername = unregisteredDeviceFacade.setUnpairedDeviceState(authentication.getName());
            PairingEvent pairingEvent = PairingEvent.builder()
                    .state(UnregisteredDeviceState.UNPAIRED)
                    .ownerUsername(tryingToPairingUsername)
                    .deviceCode(authentication.getName())
                    .build();
            pairingPublisher.publish(pairingEvent);
        }
    }
}

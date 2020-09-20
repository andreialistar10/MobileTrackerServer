package com.andrei.mobiletracker.device.message.listener.impl;

import com.andrei.mobiletracker.device.config.StompConfig;
import com.andrei.mobiletracker.device.message.event.pairing.PairingEvent;
import com.andrei.mobiletracker.device.message.listener.MobileTrackerMessageListener;
import com.andrei.mobiletracker.device.model.UnregisteredDeviceState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class PairingListener implements MobileTrackerMessageListener<PairingEvent> {

    private static final Logger logger = LogManager.getLogger(PairingListener.class);

    private static final String PREFIX_USER_PAIRING_TOPIC = "/pairing-device/";

    private static final String SUFFIX_DEVICE_PAIRING_TOPIC = "/pairing";

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    @RabbitListener(queues = "#{pairingQueue.getName()}")
    public void listen(PairingEvent message) {

        logger.info("------------------LOGGING  listen PairingEvent------------------");
        logger.info("USERNAME:     {}", message.getOwnerUsername());
        logger.info("DEVICE CODE:  {}", message.getDeviceCode());
        logger.info("DEVICE STATE: {}", message.getState());

        String topic = getDestinationTopic(message);
        simpMessagingTemplate.convertAndSend(topic, message);
        logger.info("-----------------SUCCESSFUL listen PairingEvent-----------------");
    }

    private String getDestinationTopic(PairingEvent message) {

        StringBuilder stringBuilder = new StringBuilder();
        UnregisteredDeviceState unregisteredDeviceState = message.getState();
        switch (unregisteredDeviceState) {
            case UNPAIRED: {
                stringBuilder.append(StompConfig.ERROR_TOPIC)
                        .append(PREFIX_USER_PAIRING_TOPIC)
                        .append(message.getDeviceCode())
                        .append("/user/")
                        .append(message.getOwnerUsername());
                break;
            }
            case TRYING_TO_PAIR: {
                stringBuilder.append(StompConfig.DEVICE_TOPIC)
                        .append("/")
                        .append(message.getDeviceCode())
                        .append(SUFFIX_DEVICE_PAIRING_TOPIC);
                break;
            }
            case PAIRED: {
                stringBuilder.append(StompConfig.USER_TOPIC)
                        .append("/")
                        .append(message.getOwnerUsername())
                        .append(PREFIX_USER_PAIRING_TOPIC)
                        .append(message.getDeviceCode());
            }
        }
        return stringBuilder.toString();
    }
}

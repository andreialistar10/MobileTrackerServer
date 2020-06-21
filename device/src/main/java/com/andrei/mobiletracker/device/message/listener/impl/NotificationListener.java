package com.andrei.mobiletracker.device.message.listener.impl;

import com.andrei.mobiletracker.device.config.StompConfig;
import com.andrei.mobiletracker.device.message.event.notification.NotificationEvent;
import com.andrei.mobiletracker.device.message.listener.MobileTrackerMessageListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener implements MobileTrackerMessageListener<NotificationEvent> {

    private static final Logger logger = LogManager.getLogger(NotificationListener.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final String SUFFIX_NOTIFICATION_TOPIC = "notifications";

    @Override
    @RabbitListener(queues = "#{notificationQueue.getName()}")
    public void listen(NotificationEvent notificationEvent) {
        logger.info("------------------LOGGING  listen NotificationEvent------------------");
        logger.info("DEVICE CODE:  {}", notificationEvent.getDeviceCode());
        logger.info("TYPE:         {}", notificationEvent.getType());
        String topic = getDestinationTopic(notificationEvent);
        simpMessagingTemplate.convertAndSend(topic, notificationEvent);
        logger.info("-----------------SUCCESSFUL listen NotificationEvent-----------------");
    }

    private String getDestinationTopic(NotificationEvent notificationEvent) {

        String deviceCode = notificationEvent.getDeviceCode();
        return StompConfig.DEVICE_TOPIC + "/" + deviceCode + "/" + SUFFIX_NOTIFICATION_TOPIC;
    }
}

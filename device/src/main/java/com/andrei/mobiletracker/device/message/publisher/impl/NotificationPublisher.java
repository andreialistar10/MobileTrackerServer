package com.andrei.mobiletracker.device.message.publisher.impl;

import com.andrei.mobiletracker.device.message.configurationvalue.NotificationMessage;
import com.andrei.mobiletracker.device.message.configurationvalue.PairingMessage;
import com.andrei.mobiletracker.device.message.event.notification.NotificationEvent;
import com.andrei.mobiletracker.device.message.publisher.MobileTrackerMessagePublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationPublisher implements MobileTrackerMessagePublisher<NotificationEvent> {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private NotificationMessage eventConfigs;

    @Override
    public void publish(NotificationEvent notificationEvent) {

        rabbitTemplate.convertAndSend(eventConfigs.getTopicName(),eventConfigs.getRoutingKey(), notificationEvent);
    }
}

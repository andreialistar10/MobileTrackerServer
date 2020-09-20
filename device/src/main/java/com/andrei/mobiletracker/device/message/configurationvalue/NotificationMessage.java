package com.andrei.mobiletracker.device.message.configurationvalue;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class NotificationMessage {

    @Value("${rabbitmq.topics.notification.name}")
    private String topicName;

    @Value("${rabbitmq.topics.notification.routing-key")
    private String routingKey;
}

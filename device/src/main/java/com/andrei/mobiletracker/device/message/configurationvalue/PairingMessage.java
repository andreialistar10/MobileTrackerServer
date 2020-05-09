package com.andrei.mobiletracker.device.message.configurationvalue;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PairingMessage {

    @Value("${rabbitmq.topics.pairing.name}")
    private String topicName;

    @Value("${rabbitmq.topics.pairing.routing-key")
    private String routingKey;
}

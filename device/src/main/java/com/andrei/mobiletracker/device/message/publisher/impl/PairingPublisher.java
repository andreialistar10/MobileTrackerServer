package com.andrei.mobiletracker.device.message.publisher.impl;

import com.andrei.mobiletracker.device.message.configurationvalue.PairingMessage;
import com.andrei.mobiletracker.device.message.event.pairing.PairingEvent;
import com.andrei.mobiletracker.device.message.publisher.MobileTrackerMessagePublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PairingPublisher implements MobileTrackerMessagePublisher<PairingEvent> {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PairingMessage pairingConfigs;

    @Override
    public void publish(PairingEvent messageEvent) {

        rabbitTemplate.convertAndSend(pairingConfigs.getTopicName(), pairingConfigs.getRoutingKey(), messageEvent);
    }
}

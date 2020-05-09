package com.andrei.mobiletracker.device.message.listener.impl;

import com.andrei.mobiletracker.device.message.event.pairing.PairingEvent;
import com.andrei.mobiletracker.device.message.listener.MobileTrackerMessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PairingListener implements MobileTrackerMessageListener<PairingEvent> {

    @Override
    @RabbitListener(queues = "#{pairingQueue.getName()}")
    public void listen(PairingEvent message) {

        System.out.println("Message read from pairing.device.queue : " + message.toString());
    }
}

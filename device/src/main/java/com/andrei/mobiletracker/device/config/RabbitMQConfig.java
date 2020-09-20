package com.andrei.mobiletracker.device.config;

import com.andrei.mobiletracker.device.message.configurationvalue.NotificationMessage;
import com.andrei.mobiletracker.device.message.configurationvalue.PairingMessage;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Autowired
    private PairingMessage pairingConfigs;

    @Autowired
    private NotificationMessage notificationConfigs;

    @Bean
    public Queue pairingQueue() {

        return new AnonymousQueue();
    }

    @Bean
    Queue notificationQueue() {

        return new AnonymousQueue();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Declarables topicBindings(Queue pairingQueue, Queue notificationQueue) {

        System.out.println(pairingConfigs.getTopicName());
        TopicExchange pairingTopic = new TopicExchange(pairingConfigs.getTopicName());
        TopicExchange notificationTopic = new TopicExchange(notificationConfigs.getTopicName());
        return new Declarables(
                pairingQueue,
                pairingTopic,
                BindingBuilder
                        .bind(pairingQueue)
                        .to(pairingTopic).with(pairingConfigs.getRoutingKey()),
                notificationQueue,
                notificationTopic,
                BindingBuilder
                        .bind(notificationQueue)
                        .to(notificationTopic)
                        .with(notificationConfigs.getRoutingKey()));
    }
}

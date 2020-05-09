package com.andrei.mobiletracker.device.config;

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

    @Bean
    public Queue pairingQueue() {

        return new AnonymousQueue();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Declarables topicBindings(Queue pairingQueue) {

        System.out.println(pairingConfigs.getTopicName());
        TopicExchange topicExchange = new TopicExchange(pairingConfigs.getTopicName());
        return new Declarables(
                pairingQueue,
                topicExchange,
                BindingBuilder
                        .bind(pairingQueue)
                        .to(topicExchange).with(pairingConfigs.getRoutingKey()));
    }
}

package com.example.order.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "order.status.exchange";
    public static final String QUEUE_NAME = "order.notification.queue";
    public static final String ROUTING_KEY = "order.status.update";

    @Bean
    public JacksonJsonMessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public DirectExchange orderStatusExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue orderNotificationQueue() {
        return new Queue(QUEUE_NAME, true); // durable queue
    }

    @Bean
    public Binding binding(Queue orderNotificationQueue, DirectExchange orderStatusExchange) {
        return BindingBuilder.bind(orderNotificationQueue)
                .to(orderStatusExchange)
                .with(ROUTING_KEY);
    }
}
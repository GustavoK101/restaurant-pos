package com.example.order.consumer;

import com.example.order.config.RabbitMQConfig;
import com.example.order.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleNotification(Order order) {
        log.info("Sending notification to: {}", order.getCustomer().getFullName());
        log.info("Order ID: {} is now: {}", order.getId(), order.getStatus());
        log.info("Delivery Address: {}", order.getCustomer().getAddress());
    }
}
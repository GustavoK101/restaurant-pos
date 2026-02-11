package com.example.order.consumer;

import com.example.order.config.RabbitMQConfig;
import com.example.order.model.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleNotification(Order order) {
        System.out.println("Sending notification to: " + order.getCustomer().getFullName());
        System.out.println("Order ID: " + order.getId() + " is now: " + order.getStatus());
        System.out.println("Delivery Address: " + order.getCustomer().getAddress());
    }
}
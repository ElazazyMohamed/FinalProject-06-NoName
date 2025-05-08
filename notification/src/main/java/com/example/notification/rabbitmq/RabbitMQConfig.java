package com.example.notification.rabbitmq;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;

@Configuration
public class RabbitMQConfig {
    // Exchange to listen to Reminder events
    @Bean
    public TopicExchange reminderExchange() {
        return new TopicExchange("reminder-exchange");
    }

    // Queue for Notification Microservice
    @Bean
    public Queue notificationQueue() {
        return new Queue("notification.queue");
    }

    // Bind queue to exchange for "reminder.created" events
    @Bean
    public Binding reminderCreatedBinding() {
        return BindingBuilder.bind(notificationQueue())
                .to(reminderExchange())
                .with("reminder.created");
    }
}

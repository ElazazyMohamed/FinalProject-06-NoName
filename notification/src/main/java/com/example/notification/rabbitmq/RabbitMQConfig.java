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

    @Bean
    public Queue notificationMarkReadQueue() {
        return new Queue("notification.mark.read.queue");
    }

    // Queue for listing unread notifications
    @Bean
    public Queue notificationListQueue() {
        return new Queue("notification.list.queue");
    }

    // Queue for grouping notifications by type
    @Bean
    public Queue notificationGroupQueue() {
        return new Queue("notification.group.queue");
    }

    // Bind queue to exchange for "reminder.updated" events
    @Bean
    public Binding reminderUpdatedBinding() {
        return BindingBuilder.bind(notificationQueue())
                .to(reminderExchange())
                .with("reminder.updated");
    }

    // Bind queue to exchange for "reminder.deleted" events
    @Bean
    public Binding reminderDeletedBinding() {
        return BindingBuilder.bind(notificationQueue())
                .to(reminderExchange())
                .with("reminder.deleted");
    }

    @Bean
    public Queue notificationQueryQueue() {
        return new Queue("notification.query.queue");
    }
}

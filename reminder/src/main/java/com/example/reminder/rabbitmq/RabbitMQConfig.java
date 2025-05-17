package com.example.reminder.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Exchange for reminder commands (e.g., create/snooze)
    @Bean
    public TopicExchange reminderExchange() {
        return new TopicExchange("reminder-exchange");
    }

    // Queue for the Reminder Microservice to listen to
    @Bean
    public Queue reminderQueue() {
        return new Queue("reminder-queue");
    }

    // Bind the queue to the exchange with a routing key
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(reminderQueue())
                .to(reminderExchange())
                .with("reminder.command.create");
    }

    @Bean
    public Queue reminderCommandQueue() {
        return new Queue("reminder.command.queue");
    }

    @Bean
    public Binding reminderCommandBinding() {
        return BindingBuilder.bind(reminderCommandQueue())
                .to(reminderExchange())
                .with("reminder.command.*"); // This will match all command messages
    }

}

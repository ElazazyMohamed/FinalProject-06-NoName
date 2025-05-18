package com.example.reminder.observer;

import com.example.reminder.model.Reminder;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationObserver implements ReminderObserver {
    private final RabbitTemplate rabbitTemplate;


    public NotificationObserver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void onReminderCreated(Reminder reminder) {
        // Send a "reminder created" event to RabbitMQ
        rabbitTemplate.convertAndSend(
                "reminder-exchange",
                "reminder.created", // Routing key
                reminder // Event payload
        );
    }

    @Override
    public void onReminderUpdated(Reminder reminder) {
        // Send a "reminder updated" event (e.g., snoozed)
        rabbitTemplate.convertAndSend(
                "reminder-exchange",
                "reminder.updated",
                reminder
        );
    }
}

package com.example.reminder.observer;

import com.example.reminder.model.Reminder;
import com.example.reminder.model.ReminderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationObserver implements ReminderObserver {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void onReminderCreated(Reminder reminder) {
        ReminderEvent event = createReminderEvent(reminder, "CREATED");
        rabbitTemplate.convertAndSend(
                "reminder-exchange",
                "reminder.created",
                event
        );
    }

    @Override
    public void onReminderUpdated(Reminder reminder) {
        ReminderEvent event = createReminderEvent(reminder, "UPDATED");
        rabbitTemplate.convertAndSend(
                "reminder-exchange",
                "reminder.updated",
                event
        );
    }

    @Override
    public void onReminderDeleted(Reminder reminder) {
        ReminderEvent event = createReminderEvent(reminder, "DELETED");
        rabbitTemplate.convertAndSend(
                "reminder-exchange",
                "reminder.deleted",
                event
        );
    }

    private ReminderEvent createReminderEvent(Reminder reminder, String operationType) {
        return new ReminderEvent(
                reminder.getId(),
                reminder.getUserId(),
                reminder.getNoteId(),
                reminder.getTime(),
                operationType,
                "IN_APP"  // Default notification type, could be made configurable
        );
    }
}
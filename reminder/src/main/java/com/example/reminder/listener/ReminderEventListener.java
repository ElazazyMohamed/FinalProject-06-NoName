package com.example.reminder.listener;

import com.example.reminder.factory.ReminderFactory;
import com.example.reminder.model.Reminder;
import com.example.reminder.repository.ReminderRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReminderEventListener {

    private final ReminderFactory reminderFactory;
    private final ReminderRepository reminderRepository;

    // Listen to "reminder.command.create" messages
    @RabbitListener(queues = "reminder.command.queue")
    public void handleCreateReminder(@Payload CreateReminderMessage message) {
        // Create the reminder using the factory
        Reminder reminder = reminderFactory.createReminder(message.getType());

        // Set fields from the message
        reminder.setUserId(message.getUserId());
        reminder.setNoteId(message.getNoteId());
        reminder.setTime(message.getTime());

        // Save to MongoDB
        reminderRepository.save(reminder);
    }

    // Inner class for message deserialization
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReminderMessage {
        private String type;    // e.g., "ONE_TIME"
        private Integer userId;
        private UUID noteId;
        private LocalDateTime time;
    }
}

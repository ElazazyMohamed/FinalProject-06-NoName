package com.example.reminder.listener;

import com.example.reminder.factory.ReminderFactory;
import com.example.reminder.model.Reminder;
import com.example.reminder.observer.ReminderSubject;
import com.example.reminder.repository.ReminderRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class ReminderEventListener {

    private final ReminderFactory reminderFactory;
    private final ReminderRepository reminderRepository;
    private final ReminderSubject reminderSubject;

    public void handleCreateReminder(@Payload CreateReminderMessage message) {
        // Create the reminder using the factory
        Reminder reminder = reminderFactory.createReminder(message.getType());

        // Set fields from the message
        reminder.setUserId(message.getUserId());
        reminder.setNoteId(message.getNoteId());
        reminder.setTime(message.getTime());

        Reminder savedReminder = reminderRepository.save(reminder);

        // Notify observers (similar to ReminderService)
        reminderSubject.notifyReminderCreated(savedReminder);
    }

    private void updateReminder(ReminderRequest request) {
        Reminder reminder = reminderRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Reminder not found"));
        reminder.setTime(request.getTime());
        reminderRepository.save(reminder);
    }

    // In reminder/src/main/java/com/example/reminder/listener/ReminderEventListener.java
    private void deleteReminder(ReminderRequest request) {
        // Fetch reminder before deleting it
        Reminder reminder = reminderRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Reminder not found"));

        // Delete from repository
        reminderRepository.deleteById(request.getId());

        // Notify observers
        reminderSubject.notifyReminderDeleted(reminder);
    }

    // Listen to "reminder.command.queue" messages
    @RabbitListener(queues = "reminder.command.queue")
    public void handleReminderCommand(@Payload ReminderCommand command) {
        switch (command.getAction()) {
            case "CREATE":
                handleCreateReminder((CreateReminderMessage) command.getData());
                break;
            case "UPDATE":
                updateReminder((ReminderRequest) command.getData());
                break;
            case "DELETE":
                deleteReminder((ReminderRequest) command.getData());
                break;
            default:
                throw new IllegalArgumentException("Invalid action: " + command.getAction());
        }
    }

    // Inner class for message deserialization
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReminderMessage {
        private String type;    // e.g., "ONE_TIME"
        private String userId;
        private String noteId;
        private LocalDateTime time;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReminderRequest {
        private String id;
        private LocalDateTime time;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReminderCommand {
        private String action;  // "CREATE", "UPDATE", or "DELETE"
        private Object data;    // Can be CreateReminderMessage or ReminderRequest
    }
}
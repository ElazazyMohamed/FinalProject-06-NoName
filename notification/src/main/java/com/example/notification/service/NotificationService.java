package com.example.notification.service;

import com.example.notification.dto.UserResponse;
import com.example.notification.model.ReminderEvent;
import com.example.notification.service.strategy.NotificationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final Map<String, NotificationStrategy> strategies; // Auto-injected strategies

    public void sendNotification(ReminderEvent event, UserResponse user) {
        // Choose notification strategy based on type
        String strategyType = event.getNotificationType().toLowerCase() + "Strategy";
        NotificationStrategy strategy = strategies.get(strategyType);

        if (strategy == null) {
            throw new IllegalArgumentException("Invalid strategy: " + strategyType);
        }

        // Create message content based on operation type
        String message;
        String type;

        switch (event.getOperationType()) {
            case "CREATED":
                message = "New reminder created for " + event.getTime();
                type = "REMINDER_CREATED";
                break;
            case "UPDATED":
                message = "Reminder updated for " + event.getTime();
                type = "REMINDER_UPDATED";
                break;
            case "DELETED":
                message = "Reminder deleted for " + event.getTime();
                type = "REMINDER_DELETED";
                break;
            default:
                throw new IllegalArgumentException("Invalid operation type: " + event.getOperationType());
        }

        // Send notification using the selected strategy
        strategy.send(event, user);
    }
}

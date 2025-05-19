package com.example.notification.service;

import com.example.common.models.UserDTO;
import com.example.notification.clients.UserClient;
import com.example.notification.model.ReminderEvent;
import com.example.notification.service.strategy.NotificationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final Map<String, NotificationStrategy> strategies; // Auto-injected strategies
    private final UserClient userClient; // Auto-injected Feign client


    public void sendNotification(ReminderEvent event) {
        // Fetch user info from User service
        UserDTO user = userClient.getUserById(event.getUserId());

        // Choose strategy
        String strategyType = event.getNotificationType().toLowerCase() + "Strategy";
        NotificationStrategy strategy = strategies.get(strategyType);

        if (strategy != null) {
            strategy.send(event, user);
        } else {

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

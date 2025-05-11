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
        String strategyType = event.getNotificationType().toLowerCase() + "Strategy";
        NotificationStrategy strategy = strategies.get(strategyType);
        if (strategy != null) {
            strategy.send(event, user);
        } else {
            throw new IllegalArgumentException("Invalid strategy: " + strategyType);
        }
    }
}

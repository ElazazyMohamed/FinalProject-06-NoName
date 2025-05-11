package com.example.notification.service.strategy;

import com.example.notification.dto.UserResponse;
import com.example.notification.model.ReminderEvent;
import org.springframework.stereotype.Component;

@Component("inAppStrategy")
public class InAppNotificationStrategy implements NotificationStrategy {
    @Override
    public void send(ReminderEvent event, UserResponse user) {
        String content = "Reminder: " + event.getNoteId() + " at " + event.getTime();
        // Logic to save in-app notification to MongoDB
        System.out.println("Saving in-app notification for user " + user.getId());
    }
}

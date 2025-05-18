package com.example.notification.service.strategy;

import com.example.common.models.UserDTO;
import com.example.notification.model.ReminderEvent;
import org.springframework.stereotype.Component;

@Component("emailStrategy") // Bean name matches strategy key
public class EmailNotificationStrategy implements NotificationStrategy {
    @Override
    public void send(ReminderEvent event, UserDTO user) {
        // Logic to send email
        String email = user.getEmail();
        System.out.println("Sending email to: " + email);
    }
}

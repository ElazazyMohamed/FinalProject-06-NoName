	package com.example.notification.service.strategy;

import com.example.notification.dto.UserResponse;
import com.example.notification.model.ReminderEvent;
import org.springframework.stereotype.Component;

@Component("smsStrategy")
public class SMSNotificationStrategy implements NotificationStrategy {
    @Override
    public void send(ReminderEvent event, UserResponse user) {
        String phone = user.getPhone();
        String message = "Reminder for note " + event.getNoteId() + " at " + event.getTime();
        // Logic to send SMS (e.g., integrate with Twilio)
        System.out.println("Sending SMS to " + phone + ": " + message);
    }
}

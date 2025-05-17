package com.example.notification.service.strategy;

import com.example.notification.dto.UserResponse;
import com.example.notification.model.ReminderEvent;

public interface NotificationStrategy {
    void send(ReminderEvent event, UserResponse user);
}

package com.example.notification.service.strategy;

import com.example.common.models.UserDTO;
import com.example.notification.model.ReminderEvent;

public interface NotificationStrategy {
    void send(ReminderEvent event, UserDTO user);
}

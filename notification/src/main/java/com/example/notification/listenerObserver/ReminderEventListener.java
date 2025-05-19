package com.example.notification.listenerObserver;

import com.example.common.models.UserDTO;
import com.example.notification.clients.UserClient;
import com.example.notification.model.ReminderEvent;
import com.example.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReminderEventListener {
    private final NotificationService notificationService;
    private final UserClient userClient; // Mandatory Feign client

   
    }

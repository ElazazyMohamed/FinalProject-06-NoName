package com.example.notification.listenerObserver;

import com.example.notification.dto.UserResponse;
import com.example.notification.feign.UserServiceClient;
import com.example.notification.model.ReminderEvent;
import com.example.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReminderEventListener {
    private final NotificationService notificationService;
    private final UserServiceClient userServiceClient; // Mandatory Feign client

    @RabbitListener(queues = "notification.queue")
    public void handleReminderCreated(ReminderEvent event) {
        // Fetch user data via OpenFeign (sync)
        UserResponse user = userServiceClient.getUser(String.valueOf(event.getUserId()));
        // Send notification using strategy
        notificationService.sendNotification(event, user);
    }
}

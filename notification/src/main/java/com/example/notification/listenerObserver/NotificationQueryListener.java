package com.example.notification.listenerObserver;

import com.example.notification.model.Notification;
import com.example.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationQueryListener {
    private final NotificationRepository notificationRepository;

    // Handle "list unread" requests
    @RabbitListener(queues = "notification.list.queue")
    public List<Notification> handleListUnread(String userId) {
        return notificationRepository.findByUserIdAndIsRead(userId, false);
    }

    // Handle "group by type" requests
    @RabbitListener(queues = "notification.group.queue")
    public Map<String, List<Notification>> handleGroupByType(String userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        return notifications.stream()
                .collect(Collectors.groupingBy(Notification::getType));
    }
}

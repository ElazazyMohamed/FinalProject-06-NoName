package com.example.notification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReminderEvent {
    private String reminderId;
    private String userId;
    private String noteId;
    private LocalDateTime time;
    private String notificationType; // e.g., "EMAIL", "SMS", "IN_APP"
}

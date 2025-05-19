package com.example.reminder.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReminderEvent {
    private String reminderId;
    private String userId;
    private String noteId;
    private LocalDateTime time;
    private String operationType;    // e.g., "CREATED", "UPDATED", "DELETED"
    private String notificationType; // e.g., "EMAIL", "SMS", "IN_APP"
}

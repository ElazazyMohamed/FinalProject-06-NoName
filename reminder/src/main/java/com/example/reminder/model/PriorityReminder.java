package com.example.reminder.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "reminders")
@TypeAlias("PRIORITY")
public class PriorityReminder extends AbstractReminder {

    private int priorityLevel; // 1 (low) to 5 (high)

    public PriorityReminder(String userId, LocalDateTime time, String noteID, int priorityLevel) {
        super(userId, time, noteID);
        this.priorityLevel = priorityLevel;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
}

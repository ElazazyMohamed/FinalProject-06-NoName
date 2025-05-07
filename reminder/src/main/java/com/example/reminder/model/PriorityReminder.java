package com.example.reminder.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reminders")
@TypeAlias("PRIORITY")
public class PriorityReminder extends AbstractReminder {

    private int priorityLevel; // 1 (low) to 5 (high)

    public PriorityReminder(String userId, String noteId, LocalDateTime time, int priorityLevel) {
        super(userId, noteId, time);
        this.priorityLevel = priorityLevel;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
}

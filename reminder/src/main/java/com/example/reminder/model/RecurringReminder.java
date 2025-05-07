package com.example.reminder.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reminders")
@TypeAlias("RECURRING")
public class RecurringReminder extends AbstractReminder {

    private String frequency; // e.g., "daily", "weekly", "monthly"

    public RecurringReminder(String userId, String noteId, LocalDateTime time, String frequency) {
        super(userId, noteId, time);
        this.frequency = frequency;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}

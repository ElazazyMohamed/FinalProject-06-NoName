package com.example.reminder.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "reminders")
@TypeAlias("RECURRING")
public class RecurringReminder extends AbstractReminder {

    private String frequency; // e.g., "daily", "weekly", "monthly"

    public RecurringReminder(String userId, LocalDateTime time, String noteID, String frequency) {
        super(userId, time, noteID);
        this.frequency = frequency;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}

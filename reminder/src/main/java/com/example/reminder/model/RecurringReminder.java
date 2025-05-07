package com.example.reminder.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class RecurringReminder extends AbstractReminder {

    private String frequency; // e.g., "daily", "weekly", "monthly"

    public RecurringReminder(String userId, String noteId, LocalDateTime time, String frequency) {
        super(userId, noteId, time);
        this.frequency = frequency;
        setRepeated(true);
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}

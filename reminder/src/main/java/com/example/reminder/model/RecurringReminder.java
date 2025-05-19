package com.example.reminder.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "reminders")
@TypeAlias("RECURRING")
public class RecurringReminder extends AbstractReminder {

    private RecurrenceRule recurrenceRule;
    private LocalDateTime lastTriggeredTime;

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

    public RecurrenceRule getRecurrenceRule() {
        return recurrenceRule;
    }

    public void setRecurrenceRule(RecurrenceRule recurrenceRule) {
        this.recurrenceRule = recurrenceRule;
    }

    public LocalDateTime getLastTriggeredTime() {
        return lastTriggeredTime;
    }

    public void setLastTriggeredTime(LocalDateTime lastTriggeredTime) {
        this.lastTriggeredTime = lastTriggeredTime;
    }
}

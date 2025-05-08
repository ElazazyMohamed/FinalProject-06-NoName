package com.example.reminder.observer;

import com.example.reminder.model.Reminder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReminderSubject {
    private final List<ReminderObserver> observers = new ArrayList<>();

    // Register observers (e.g., NotificationObserver)
    public void attach(ReminderObserver observer) {
        observers.add(observer);
    }

    // Notify all observers when a reminder is created
    public void notifyReminderCreated(Reminder reminder) {
        observers.forEach(observer -> observer.onReminderCreated(reminder));
    }

    // Notify all observers when a reminder is updated (e.g., snoozed)
    public void notifyReminderUpdated(Reminder reminder) {
        observers.forEach(observer -> observer.onReminderUpdated(reminder));
    }
}

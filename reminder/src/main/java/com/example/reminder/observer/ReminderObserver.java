package com.example.reminder.observer;

import com.example.reminder.model.Reminder;

public interface ReminderObserver {
    void onReminderCreated(Reminder reminder);
    void onReminderUpdated(Reminder reminder); // Optional: For snooze/update events
}

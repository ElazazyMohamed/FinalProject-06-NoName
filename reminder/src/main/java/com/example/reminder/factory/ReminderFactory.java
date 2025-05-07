package com.example.reminder.factory;

import com.example.reminder.model.Reminder;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public interface ReminderFactory {

    void registerReminder(String type, Supplier<? extends Reminder> constructor);
    Reminder createReminder(String type);
}
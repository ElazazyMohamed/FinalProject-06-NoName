package com.example.reminder.factory;

import com.example.reminder.model.Reminder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Component
public interface ReminderFactory {

    void registerReminder(String type, Supplier<? extends Reminder> constructor);
    Reminder createReminder(String type);
}
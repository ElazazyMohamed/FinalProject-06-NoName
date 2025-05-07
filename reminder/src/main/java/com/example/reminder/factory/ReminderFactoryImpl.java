package com.example.reminder.factory;

import com.example.reminder.model.Reminder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ReminderFactoryImpl implements ReminderFactory {

    private final Map<String, Supplier<? extends Reminder>> registry = new HashMap<>();

    @Override
    public void registerReminder(String type, Supplier<? extends Reminder> constructor) {
        registry.put(type, constructor);
    }

    @Override
    public Reminder createReminder(String type) {
        Supplier<? extends Reminder> constructor = registry.get(type);
        if (constructor == null) {
            throw new IllegalArgumentException("Unknown reminder type: " + type);
        }
        return constructor.get();
    }
}

package com.example.reminder.factory;

import com.example.reminder.model.AbstractReminder;
import com.example.reminder.model.Reminder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.stereotype.Component;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class ReminderFactoryImpl implements ReminderFactory {

    private final Map<String, Supplier<Reminder>> registry = new HashMap<>();

    public ReminderFactoryImpl() {
        scanAndRegisterReminders();
    }

    @Override
    public void registerReminder(String type, Supplier<? extends Reminder> constructor) {
        registry.put(type, constructor::get);
    }

    private void scanAndRegisterReminders() {
        try {
            // Scan package "com.example.reminder.model" for classes extending AbstractReminder
            ClassPathScanningCandidateComponentProvider scanner =
                    new ClassPathScanningCandidateComponentProvider(false);
            scanner.addIncludeFilter(new AssignableTypeFilter(AbstractReminder.class));

            for (BeanDefinition bd : scanner.findCandidateComponents("com.example.reminder.model")) {
                Class<?> clazz = Class.forName(bd.getBeanClassName());
                if (clazz.isAnnotationPresent(TypeAlias.class)) {
                    TypeAlias typeAlias = clazz.getAnnotation(TypeAlias.class);
                    String type = typeAlias.value();
                    Constructor<?> constructor = clazz.getDeclaredConstructor();
                    constructor.setAccessible(true); // Ensure no-args constructor is accessible
                    registry.put(type, () -> {
                        try {
                            return (Reminder) constructor.newInstance();
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException("Failed to instantiate reminder of type: " + type, e);
                        }
                    });
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to register reminders via reflection", e);
        }
    }

    @Override
    public Reminder createReminder(String type) {
        Supplier<Reminder> supplier = registry.get(type);
        if (supplier == null) throw new IllegalArgumentException("Unknown type: " + type);
        return supplier.get();
    }
}

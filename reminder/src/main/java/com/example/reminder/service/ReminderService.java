package com.example.reminder.service;

import com.example.reminder.factory.ReminderFactory;
import com.example.reminder.model.Reminder;
import com.example.reminder.observer.ReminderSubject;
import com.example.reminder.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private final ReminderFactory reminderFactory;
    private final ReminderSubject reminderSubject; // Observer subject


    public Reminder createReminder(String type, Integer userId, UUID noteId, LocalDateTime time) {
        // Create the reminder
        Reminder reminder = reminderFactory.createReminder(type);
        reminder.setUserId(userId);
        reminder.setNoteId(noteId);
        reminder.setTime(time);

        // Save to MongoDB
        Reminder savedReminder = reminderRepository.save(reminder);

        // Notify observers (e.g., NotificationObserver)
        reminderSubject.notifyReminderCreated(savedReminder);

        return savedReminder;
    }

    public Reminder snoozeReminder(String reminderId, LocalDateTime newTime) {
        Reminder reminder = reminderRepository.findById(reminderId)
                .orElseThrow(() -> new RuntimeException("Reminder not found"));
        reminder.setSnoozed(newTime);
        reminder.setTime(newTime);

        Reminder updatedReminder = reminderRepository.save(reminder);

        // Notify observers of the update
        reminderSubject.notifyReminderUpdated(updatedReminder);

        return updatedReminder;
    }
}

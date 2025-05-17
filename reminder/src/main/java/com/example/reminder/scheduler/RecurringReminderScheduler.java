package com.example.reminder.scheduler;

import com.example.reminder.factory.ReminderFactory;
import com.example.reminder.model.RecurringReminder;
import com.example.reminder.model.Reminder;
import com.example.reminder.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecurringReminderScheduler {
    private final ReminderRepository reminderRepository;
    private final ReminderFactory reminderFactory;

    // Run every 1 hour (adjust interval as needed)
    @Scheduled(fixedRate = 3600 * 1000)
    public void regenerateRecurringReminders() {
        // Fetch all recurring reminders
        List<RecurringReminder> recurringReminders = reminderRepository.findByRecurrenceRuleNotNull();

        for (RecurringReminder reminder : recurringReminders) {
            LocalDateTime nextTriggerTime = calculateNextTriggerTime(
                    reminder.getLastTriggeredTime(),
                    reminder.getRecurrenceRule()
            );

            if (nextTriggerTime.isBefore(LocalDateTime.now())) {
                // Create a new reminder
                Reminder newReminder = reminderFactory.createReminder("RECURRING");
                newReminder.setUserId(reminder.getUserId());
                newReminder.setNoteId(reminder.getNoteId());
                newReminder.setTime(nextTriggerTime);
                reminderRepository.save(newReminder);

                // Update lastTriggeredTime
                reminder.setLastTriggeredTime(LocalDateTime.now());
                reminderRepository.save(reminder);

                log.info("Regenerated recurring reminder: {}", newReminder.getId());
            }
        }
    }

    private LocalDateTime calculateNextTriggerTime(LocalDateTime lastTriggeredTime, RecurrenceRule rule) {
        if (lastTriggeredTime == null) return LocalDateTime.now();

        return switch (rule) {
            case DAILY -> lastTriggeredTime.plusDays(1);
            case WEEKLY -> lastTriggeredTime.plusWeeks(1);
            case MONTHLY -> lastTriggeredTime.plusMonths(1);
            default -> throw new IllegalArgumentException("Unsupported recurrence rule: " + rule);
        };
    }
}

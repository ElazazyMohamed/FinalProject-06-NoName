package com.example.reminder.repository;

import com.example.reminder.model.RecurringReminder;
import com.example.reminder.model.Reminder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ReminderRepository extends MongoRepository<Reminder, String> {

    // Custom query methods can be defined here if needed
    // For example, find reminders by userId or noteId
     List<Reminder> findByUserId(String userId);
     List<Reminder> findByNoteId(String noteId);

    @Query("{ '_class' : 'RECURRING' }")
    List<RecurringReminder> findByRecurrenceRuleNotNull();

}

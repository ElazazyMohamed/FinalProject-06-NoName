package com.example.reminder.repository;

import com.example.reminder.model.Reminder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReminderRepository extends MongoRepository<Reminder, String> {

}

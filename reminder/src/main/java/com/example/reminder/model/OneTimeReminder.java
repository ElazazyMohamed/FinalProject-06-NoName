package com.example.reminder.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reminders")
@TypeAlias("ONE_TIME")
public class OneTimeReminder extends  AbstractReminder {

    public OneTimeReminder(String userId, String noteId, LocalDateTime time) {
        super(userId, noteId, time);
    }


}

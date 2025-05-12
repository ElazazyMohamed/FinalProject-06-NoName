package com.example.reminder.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "reminders")
@TypeAlias("ONE_TIME")
public class OneTimeReminder extends  AbstractReminder {

    public OneTimeReminder(Integer userId, LocalDateTime time, UUID noteID) {
        super(userId, time, noteID);
    }


}

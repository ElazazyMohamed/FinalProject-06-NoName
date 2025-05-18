package com.example.reminder.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@Document(collection = "reminders")
@TypeAlias("ONE_TIME")
public class OneTimeReminder extends  AbstractReminder {

    public OneTimeReminder(String userId, LocalDateTime time, String noteID) {
        super(userId, time, noteID);
    }


}

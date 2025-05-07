package com.example.reminder.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reminders")
public abstract class AbstractReminder implements Reminder {

    @Id
    private String id;
    private String userId;
    private String noteId;
    private LocalDateTime time;
    private boolean repeated;
    private boolean snoozed;


    public AbstractReminder() {}

    public AbstractReminder(String userId, String noteId, LocalDateTime time) {
        this.userId = userId;
        this.noteId = noteId;
        this.time = time;
        this.repeated = false;
        this.snoozed = false;
    }


    @Override
    public void setSnoozed(LocalDateTime newTime) {
        this.snoozed = true;
        this.time = newTime;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getNoteId() {
        return noteId;
    }

    @Override
    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public boolean isRepeated() {
        return repeated;
    }

    @Override
    public boolean isSnoozed() {
        return snoozed;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    @Override
    public void setTime(LocalDateTime time) {
        this.time = time;
    }





}
















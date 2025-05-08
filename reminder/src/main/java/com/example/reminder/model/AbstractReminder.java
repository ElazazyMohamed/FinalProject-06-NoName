package com.example.reminder.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "reminders")
public abstract class AbstractReminder implements Reminder {

    @Id
    private UUID id;
    private Integer userId;
    private UUID noteId;
    private LocalDateTime time;
    private boolean repeated;
    private boolean snoozed;


    public AbstractReminder() {}

    public AbstractReminder(Integer userId, LocalDateTime time, UUID noteID) {
        this.userId = userId;
        this.noteId = noteID;
        this.id = UUID.randomUUID();
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
    public UUID getId() {
        return id;
    }

    @Override
    public Integer getUserId() {
        return userId;
    }

    @Override
    public UUID getNoteId() {
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
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public void setNoteId(UUID noteId) {
        this.noteId = noteId;
    }

    @Override
    public void setTime(LocalDateTime time) {
        this.time = time;
    }





}
















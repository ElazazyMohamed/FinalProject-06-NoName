package com.example.reminder.model;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "reminders")
public abstract class AbstractReminder implements Reminder {

    @Id
    private ObjectId id;
    private String userId;
    private String noteId;
    private LocalDateTime time;
    private boolean repeated;
    private boolean snoozed;


    public AbstractReminder() {}

    public AbstractReminder(String userId, LocalDateTime time, String noteID) {
        this.userId = userId;
        this.noteId = noteID;
        this.id = new ObjectId();
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
        return id != null ? id.toString() : null;
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
    public void setId(String id) {
        this.id = id != null ? new ObjectId(id) : null;
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
















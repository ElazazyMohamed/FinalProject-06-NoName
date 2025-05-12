package com.example.reminder.model;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Reminder {


    String getId();
    String getUserId();
    String getNoteId();
    LocalDateTime getTime();
    boolean isRepeated();
    boolean isSnoozed();
    void setSnoozed(LocalDateTime newTime);
    void setUserId(String userId);
    void setNoteId(String noteId);
    void setId(String id);
    void setTime(LocalDateTime time);




}

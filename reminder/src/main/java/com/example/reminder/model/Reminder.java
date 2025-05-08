package com.example.reminder.model;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Reminder {

    UUID getId();
    Integer getUserId();
    UUID getNoteId();
    LocalDateTime getTime();
    boolean isRepeated();
    boolean isSnoozed();
    void setSnoozed(LocalDateTime newTime);
    void setUserId(Integer userId);
    void setNoteId(UUID noteId);
    void setId(UUID id);
    void setTime(LocalDateTime time);




}

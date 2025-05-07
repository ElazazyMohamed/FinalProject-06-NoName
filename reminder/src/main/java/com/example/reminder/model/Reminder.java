package com.example.reminder.model;

import java.time.LocalDateTime;

public interface Reminder {

    String getId();
    String getUserId();
    String getNoteId();
    LocalDateTime getTime();
    boolean isRepeated();
    boolean isSnoozed();
    void setSnoozed(LocalDateTime newTime);
    void setRepeated(boolean repeated);


}

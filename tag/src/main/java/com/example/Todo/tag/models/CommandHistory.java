package com.example.Todo.tag.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "command_history")
public class CommandHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String commandType;

    @Column(nullable = false)
    private String commandDetails;

    @Column(nullable = false)
    private String executedBy;

    @Column(columnDefinition = "TEXT")
    private String resultData;

    private boolean isUndone;

    private LocalDateTime executedAt;

    private LocalDateTime undoneAt;

    // Constructors
    public CommandHistory() {
        this.executedAt = LocalDateTime.now();
        this.isUndone = false;
    }

    public CommandHistory(String commandType, String commandDetails, String executedBy, String resultData) {
        this.commandType = commandType;
        this.commandDetails = commandDetails;
        this.executedBy = executedBy;
        this.resultData = resultData;
        this.executedAt = LocalDateTime.now();
        this.isUndone = false;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getCommandDetails() {
        return commandDetails;
    }

    public void setCommandDetails(String commandDetails) {
        this.commandDetails = commandDetails;
    }

    public String getExecutedBy() {
        return executedBy;
    }

    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    public boolean isUndone() {
        return isUndone;
    }

    public void setUndone(boolean undone) {
        isUndone = undone;
        if (undone) {
            this.undoneAt = LocalDateTime.now();
        }
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }

    public LocalDateTime getUndoneAt() {
        return undoneAt;
    }

    public void setUndoneAt(LocalDateTime undoneAt) {
        this.undoneAt = undoneAt;
    }
}

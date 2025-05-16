package com.example.Todo.tag.dto;

import com.example.Todo.tag.models.CommandHistory;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for CommandHistory entities
 */
public class CommandHistoryDTO {

    private Long id;
    private String commandType;
    private String commandDetails;
    private String executedBy;
    private String resultData;
    private boolean isUndone;
    private LocalDateTime executedAt;
    private LocalDateTime undoneAt;

    // Constructors
    public CommandHistoryDTO() {
    }

    public CommandHistoryDTO(Long id, String commandType, String commandDetails, String executedBy,
            String resultData, boolean isUndone, LocalDateTime executedAt, LocalDateTime undoneAt) {
        this.id = id;
        this.commandType = commandType;
        this.commandDetails = commandDetails;
        this.executedBy = executedBy;
        this.resultData = resultData;
        this.isUndone = isUndone;
        this.executedAt = executedAt;
        this.undoneAt = undoneAt;
    }

    // Factory method to create DTO from entity
    public static CommandHistoryDTO fromEntity(CommandHistory commandHistory) {
        return new CommandHistoryDTO(
                commandHistory.getId(),
                commandHistory.getCommandType(),
                commandHistory.getCommandDetails(),
                commandHistory.getExecutedBy(),
                commandHistory.getResultData(),
                commandHistory.isUndone(),
                commandHistory.getExecutedAt(),
                commandHistory.getUndoneAt());
    }

    // Convert DTO to entity
    public CommandHistory toEntity() {
        CommandHistory commandHistory = new CommandHistory();
        commandHistory.setId(this.id);
        commandHistory.setCommandType(this.commandType);
        commandHistory.setCommandDetails(this.commandDetails);
        commandHistory.setExecutedBy(this.executedBy);
        commandHistory.setResultData(this.resultData);
        commandHistory.setUndone(this.isUndone);
        if (this.executedAt != null) {
            commandHistory.setExecutedAt(this.executedAt);
        }
        if (this.undoneAt != null) {
            commandHistory.setUndoneAt(this.undoneAt);
        }
        return commandHistory;
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
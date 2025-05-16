package com.example.todo.logging.model;

/**
 * Request model for creating new log entries
 */
public class LogRequest {
    private String message;
    private LogLevel level;
    private LogType type;
    private String source;
    private String userId;
    private Object payload;

    // Default constructor
    public LogRequest() {
    }

    public LogRequest(String message, LogLevel level, LogType type, String source) {
        this.message = message;
        this.level = level;
        this.type = type;
        this.source = source;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
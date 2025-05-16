package com.example.todo.logging.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "logs")
public class Log {
    @Id
    private String id;
    private String message;
    private LogLevel level;
    private LogType type;
    private String source;
    private String userId;
    private LocalDateTime timestamp;
    private Object payload;

    // Default constructor
    public Log() {
        this.timestamp = LocalDateTime.now();
    }

    // Constructor with essential fields
    public Log(String message, LogLevel level, LogType type, String source) {
        this();
        this.message = message;
        this.level = level;
        this.type = type;
        this.source = source;
    }

    // Constructor with all fields
    public Log(String message, LogLevel level, LogType type, String source, String userId, Object payload) {
        this(message, level, type, source);
        this.userId = userId;
        this.payload = payload;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
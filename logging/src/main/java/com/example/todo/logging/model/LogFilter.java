package com.example.todo.logging.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Model class for filtering logs based on various criteria
 */
public class LogFilter {
    private List<LogLevel> levels;
    private List<LogType> types;
    private String messageContains;
    private String userId;
    private String source;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer page;
    private Integer size;
    private String sortBy;
    private Boolean ascending;

    // Default constructor
    public LogFilter() {
        this.page = 0;
        this.size = 20;
        this.sortBy = "timestamp";
        this.ascending = false;
    }

    // Getters and Setters
    public List<LogLevel> getLevels() {
        return levels;
    }

    public void setLevels(List<LogLevel> levels) {
        this.levels = levels;
    }

    public List<LogType> getTypes() {
        return types;
    }

    public void setTypes(List<LogType> types) {
        this.types = types;
    }

    public String getMessageContains() {
        return messageContains;
    }

    public void setMessageContains(String messageContains) {
        this.messageContains = messageContains;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getAscending() {
        return ascending;
    }

    public void setAscending(Boolean ascending) {
        this.ascending = ascending;
    }
}
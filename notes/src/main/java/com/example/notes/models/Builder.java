package com.example.notes.models;

import lombok.Getter;

import java.time.Instant;
import java.util.List;

// Builder interface for creating Note objects

public interface Builder {
    public String getTitle();
    public Builder setTitle(String title);
    public String getContent();
    public Builder setContent(String content);
    public Integer getUserId();
    public Builder setUserId(Integer userId);
    public List<String> getTags();
    public Builder setTags(List<String> tags);
    public Instant getCreatedAt();
    public Builder setCreatedAt(Instant createdAt);
    public Instant getUpdatedAt();
    public Builder setUpdatedAt(Instant updatedAt);
    public Status getStatus();
    public Builder setStatus(Status status);
    public Note build();
}

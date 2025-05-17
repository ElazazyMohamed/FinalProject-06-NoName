package com.example.notes.models;

import lombok.Getter;
import java.time.Instant;
import java.util.List;

// Builder for creating Note objects
@Getter
public class NoteBuilder implements Builder {
    private String title;
    private String content;
    private Integer userId;
    private List<String> tags;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
    private Status status = Status.UNARCHIVED;


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Builder setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Builder setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public Integer getUserId() {
        return userId;
    }

    @Override
    public Builder setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public List<String> getTags() {
        return tags;
    }

    @Override
    public Builder setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public Builder setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public Builder setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public Builder setStatus(Status status) {
        this.status = status;
        return this;
    }

    @Override
    public Note build() {
        return new Note(this);
    }
}

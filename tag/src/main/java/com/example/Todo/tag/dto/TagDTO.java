package com.example.Todo.tag.dto;

import com.example.Todo.tag.models.Tag;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Tag entities
 */
public class TagDTO {

    private Long id;
    private String name;
    private String color;
    private String description;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public TagDTO() {
    }

    public TagDTO(Long id, String name, String color, String description, Long userId,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.description = description;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Factory method to create DTO from entity
    public static TagDTO fromEntity(Tag tag) {
        return new TagDTO(
                tag.getId(),
                tag.getName(),
                tag.getColor(),
                tag.getDescription(),
                tag.getUserId(),
                tag.getCreatedAt(),
                tag.getUpdatedAt());
    }

    // Convert DTO to entity
    public Tag toEntity() {
        Tag tag = new Tag();
        tag.setId(this.id);
        tag.setName(this.name);
        tag.setColor(this.color);
        tag.setDescription(this.description);
        tag.setUserId(this.userId);
        if (this.createdAt != null) {
            tag.setCreatedAt(this.createdAt);
        }
        if (this.updatedAt != null) {
            tag.setUpdatedAt(this.updatedAt);
        }
        return tag;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
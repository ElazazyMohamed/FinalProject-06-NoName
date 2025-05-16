package com.example.Todo.tag.dto;

import com.example.Todo.tag.models.TagNote;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for TagNote entities
 */
public class TagNoteDTO {

    private Long id;
    private Long tagId;
    private String noteId;
    private LocalDateTime createdAt;

    // Constructors
    public TagNoteDTO() {
    }

    public TagNoteDTO(Long id, Long tagId, String noteId, LocalDateTime createdAt) {
        this.id = id;
        this.tagId = tagId;
        this.noteId = noteId;
        this.createdAt = createdAt;
    }

    // Factory method to create DTO from entity
    public static TagNoteDTO fromEntity(TagNote tagNote) {
        return new TagNoteDTO(
                tagNote.getId(),
                tagNote.getTagId(),
                tagNote.getNoteId(),
                tagNote.getCreatedAt());
    }

    // Convert DTO to entity
    public TagNote toEntity() {
        TagNote tagNote = new TagNote();
        tagNote.setId(this.id);
        tagNote.setTagId(this.tagId);
        tagNote.setNoteId(this.noteId);
        if (this.createdAt != null) {
            tagNote.setCreatedAt(this.createdAt);
        }
        return tagNote;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
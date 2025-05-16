package com.example.Todo.tag.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tag_notes")
public class TagNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tagId;

    @Column(nullable = false)
    private String noteId;

    private LocalDateTime createdAt;

    // Constructors
    public TagNote() {
        this.createdAt = LocalDateTime.now();
    }

    public TagNote(Long tagId, String noteId) {
        this.tagId = tagId;
        this.noteId = noteId;
        this.createdAt = LocalDateTime.now();
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
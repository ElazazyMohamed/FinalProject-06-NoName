package com.example.notes.models;

public class ArchiveStrategy implements StatusStrategy {
    @Override
    public void applyStatus(Note note) {
        note.setStatus(Status.ARCHIVED);
    }
}

package com.example.notes.models;

public class UnarchiveStrategy implements StatusStrategy {
    @Override
    public void applyStatus(Note note) {
        note.setStatus(Status.UNARCHIVED);
    }
}

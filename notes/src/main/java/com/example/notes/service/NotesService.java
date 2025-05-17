package com.example.notes.service;

import com.example.notes.models.*;
import com.example.notes.repository.NotesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotesService {
    private final NotesRepository notesRepository;

    public Note createNote(NoteRequest noteRequest, Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        NoteBuilder noteBuilder = new NoteBuilder();
        noteBuilder.setTitle(noteRequest.getTitle());
        noteBuilder.setContent(noteRequest.getContent());
        noteBuilder.setUserId(userId);
        noteBuilder.setTags(noteRequest.getTags());
        Note note = noteBuilder.build();
        return notesRepository.save(note);
    }
    public Optional<Note> updateNote(String id, Integer userId, NoteRequest noteRequest) {
        if (id == null || userId == null) {
            throw new IllegalArgumentException("Note ID and User ID are required");
        }
        return notesRepository.findByIdAndUserId(id, userId)
                .map(note -> {
                    note.setTitle(noteRequest.getTitle());
                    note.setContent(noteRequest.getContent());
                    note.setTags(noteRequest.getTags());
                    note.setUpdatedAt(Instant.now());
                    return notesRepository.save(note);
                    });
                }
}
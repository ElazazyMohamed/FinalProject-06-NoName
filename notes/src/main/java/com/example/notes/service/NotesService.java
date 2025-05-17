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

    public List<Note> getNotesByUser(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        return notesRepository.findByUserId(userId);
    }

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

    public boolean deleteNote(String id, Integer userId) {
        if (id == null || userId == null) {
            throw new IllegalArgumentException("Note ID and User ID are required");
        }
        Optional<Note> note = notesRepository.findByIdAndUserId(id, userId);
        if (note.isPresent()) {
            notesRepository.deleteById(id);
            return true;
        }
        return false;
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

    private Optional<Note> updateStatus(String id, Integer userId, StatusStrategy statusStrategy) {
        if (id == null || userId == null) {
            throw new IllegalArgumentException("Note ID and User ID are required");
        }
        return notesRepository.findByIdAndUserId(id, userId)
                .map(note -> {
                    statusStrategy.applyStatus(note);
                    return notesRepository.save(note);
                });
    }

    public Optional<Note> archiveNote(String id, Integer userId) {
        return updateStatus(id, userId, new ArchiveStrategy());
    }

    public Optional<Note> unarchiveNote(String id, Integer userId) {
        return updateStatus(id, userId, new UnarchiveStrategy());
    }
}

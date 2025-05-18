package com.example.notes.service;

import com.example.common.models.UserDTO;
import com.example.notes.clients.UserClient;
import com.example.notes.models.*;
import com.example.notes.rabbitmq.RabbitMQProducer;
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
    private final UserClient userClient;
    private RabbitMQProducer rabbitMQProducer;

    public List<Note> getNotesByUser(Integer userId) {
        validateActiveUser(userId);
        return notesRepository.findByUserId(userId);
    }

    public Note createNote(NoteRequest noteRequest, Integer userId) {
        validateActiveUser(userId);

        NoteBuilder noteBuilder = new NoteBuilder();
        noteBuilder.setTitle(noteRequest.getTitle());
        noteBuilder.setContent(noteRequest.getContent());
        noteBuilder.setUserId(userId);
        noteBuilder.setTags(noteRequest.getTags());

        Note note = noteBuilder.build();
        this.rabbitMQProducer.sendToLogging(
                String.format("action=create; entity=note; id=%s; source=notes-service;", note.getId()),
                "create"
        );
        return notesRepository.save(note);
    }

    public boolean deleteNote(String id, Integer userId) {
        validateUserId(userId);

        Optional<Note> note = notesRepository.findByIdAndUserId(id, userId);
        if (note.isPresent()) {
            this.rabbitMQProducer.sendToLogging(
                    String.format("action=delete; entity=note; id=%s; source=notes-service;", note.get().getId()),
                    "delete"
            );
            notesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Note> updateNote(String id, Integer userId, NoteRequest noteRequest) {
        validateUserId(userId);

        return notesRepository.findByIdAndUserId(id, userId)
                .map(note -> {
                    note.setTitle(noteRequest.getTitle());
                    note.setContent(noteRequest.getContent());
                    note.setTags(noteRequest.getTags());
                    note.setUpdatedAt(Instant.now());
                    this.rabbitMQProducer.sendToLogging(
                            String.format("action=update; entity=note; id=%s; source=notes-service;", note.getId()),
                            "update"
                    );
                    return notesRepository.save(note);
                });
    }

    private Optional<Note> updateStatus(String id, Integer userId, StatusStrategy statusStrategy) {
        validateUserId(userId);

        return notesRepository.findByIdAndUserId(id, userId)
                .map(note -> {
                    statusStrategy.applyStatus(note);
                    this.rabbitMQProducer.sendToLogging(
                            String.format("action=%s; entity=note; id=%s; source=notes-service;",
                                    statusStrategy instanceof ArchiveStrategy ? "archive" : "unarchive",
                                    note.getId()
                            ),
                            "update"
                    );
                    return notesRepository.save(note);
                });
    }

    public Optional<Note> archiveNote(String id, Integer userId) {
        return updateStatus(id, userId, new ArchiveStrategy());
    }

    public Optional<Note> unarchiveNote(String id, Integer userId) {
        return updateStatus(id, userId, new UnarchiveStrategy());
    }

    // Helper
    private void validateUserId(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }
    }

    private void validateActiveUser(Integer userId) {
        validateUserId(userId);
        UserDTO userDTO = userClient.getUserById(userId);
        if (!"ACTIVE".equalsIgnoreCase(userDTO.getStatus().name())) {
            throw new IllegalStateException("User is not active");
        }
    }


}

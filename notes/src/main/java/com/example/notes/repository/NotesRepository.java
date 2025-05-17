package com.example.notes.repository;

import com.example.notes.models.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface NotesRepository extends MongoRepository<Note, String> {
    List<Note> findByUserId(Integer userId);
    Note findNoteById(String id);
    Optional<Note> findByIdAndUserId(String id, Integer userId);
}

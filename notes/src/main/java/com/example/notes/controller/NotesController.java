package com.example.notes.controller;

import com.example.notes.models.Note;
import com.example.notes.models.NoteRequest;
import com.example.notes.service.NotesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
@AllArgsConstructor
public class NotesController {
    private final NotesService notesService;

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody NoteRequest noteRequest, @RequestParam Integer userId) {
        Note note = notesService.createNote(noteRequest, userId);
        return ResponseEntity.ok(note);
    }

    @GetMapping
    public ResponseEntity<List<Note>> getNotesByUser(@RequestParam Integer userId) {
        List<Note> notes = notesService.getNotesByUser(userId);
        return ResponseEntity.ok(notes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(
            @PathVariable String id,
            @RequestParam Integer userId,
            @RequestBody NoteRequest request) {
        return notesService.updateNote(id, userId, request)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/archive")
    public ResponseEntity<Note> archiveNote(@PathVariable String id, @RequestParam Integer userId) {
        return notesService.archiveNote(id, userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/unarchive")
    public ResponseEntity<Note> unarchiveNote(@PathVariable String id, @RequestParam Integer userId) {
        return notesService.unarchiveNote(id, userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id, @RequestParam Integer userId) {
        boolean deleted = notesService.deleteNote(id, userId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

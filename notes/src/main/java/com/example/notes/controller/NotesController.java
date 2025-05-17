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
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(
            @PathVariable String id,
            @RequestParam Integer userId,
            @RequestBody NoteRequest request) {
        return notesService.updateNote(id, userId, request)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
        }
}
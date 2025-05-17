package com.example.Todo.tag.controllers;

import com.example.Todo.tag.dto.ApiResponse;
import com.example.Todo.tag.dto.TagNoteDTO;
import com.example.Todo.tag.models.TagNote;
import com.example.Todo.tag.services.TagNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tag-notes")
public class TagNoteController {

    private final TagNoteService tagNoteService;

    @Autowired
    public TagNoteController(TagNoteService tagNoteService) {
        this.tagNoteService = tagNoteService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TagNoteDTO>>> getAllTagNotes() {
        List<TagNoteDTO> tagNotes = tagNoteService.getAllTagNotes().stream()
                .map(TagNoteDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(tagNotes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TagNoteDTO>> getTagNoteById(@PathVariable Long id) {
        return tagNoteService.getTagNoteById(id)
                .map(tagNote -> ResponseEntity.ok(ApiResponse.success(TagNoteDTO.fromEntity(tagNote))))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Tag-Note link not found with id: " + id)));
    }

    @GetMapping("/tag/{tagId}")
    public ResponseEntity<ApiResponse<List<TagNoteDTO>>> getTagNotesByTagId(@PathVariable Long tagId) {
        List<TagNoteDTO> tagNotes = tagNoteService.getTagNotesByTagId(tagId).stream()
                .map(TagNoteDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(tagNotes));
    }

    @GetMapping("/note/{noteId}")
    public ResponseEntity<ApiResponse<List<TagNoteDTO>>> getTagNotesByNoteId(@PathVariable String noteId) {
        List<TagNoteDTO> tagNotes = tagNoteService.getTagNotesByNoteId(noteId).stream()
                .map(TagNoteDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(tagNotes));
    }

    @PostMapping("/link")
    public ResponseEntity<ApiResponse<TagNoteDTO>> linkTagToNote(
            @RequestParam Long tagId,
            @RequestParam String noteId,
            @RequestHeader(value = "X-Username", defaultValue = "system") String username) {
        TagNote tagNote = tagNoteService.linkTagToNote(tagId, noteId, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tag linked to note successfully", TagNoteDTO.fromEntity(tagNote)));
    }

    @DeleteMapping("/unlink")
    public ResponseEntity<ApiResponse<Void>> unlinkTagFromNote(
            @RequestParam Long tagId,
            @RequestParam String noteId,
            @RequestHeader(value = "X-Username", defaultValue = "system") String username) {
        boolean result = tagNoteService.unlinkTagFromNote(tagId, noteId, username);
        if (result) {
            return ResponseEntity.ok(ApiResponse.success("Tag unlinked from note successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Tag-Note link not found"));
        }
    }

    @DeleteMapping("/tag/{tagId}")
    public ResponseEntity<ApiResponse<Void>> removeAllLinksForTag(
            @PathVariable Long tagId,
            @RequestHeader(value = "X-Username", defaultValue = "system") String username) {
        boolean result = tagNoteService.removeAllLinksForTag(tagId, username);
        if (result) {
            return ResponseEntity.ok(ApiResponse.success("All links for tag removed successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("No links found for tag with id: " + tagId));
        }
    }

    @DeleteMapping("/note/{noteId}")
    public ResponseEntity<ApiResponse<Void>> removeAllLinksForNote(
            @PathVariable String noteId,
            @RequestHeader(value = "X-Username", defaultValue = "system") String username) {
        boolean result = tagNoteService.removeAllLinksForNote(noteId, username);
        if (result) {
            return ResponseEntity.ok(ApiResponse.success("All links for note removed successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("No links found for note with id: " + noteId));
        }
    }
}
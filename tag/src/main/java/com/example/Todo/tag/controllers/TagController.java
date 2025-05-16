package com.example.Todo.tag.controllers;

import com.example.Todo.tag.dto.ApiResponse;
import com.example.Todo.tag.dto.TagDTO;
import com.example.Todo.tag.models.Tag;
import com.example.Todo.tag.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TagDTO>>> getAllTags() {
        List<TagDTO> tags = tagService.getAllTags().stream()
                .map(TagDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(tags));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TagDTO>> getTagById(@PathVariable Long id) {
        return tagService.getTagById(id)
                .map(tag -> ResponseEntity.ok(ApiResponse.success(TagDTO.fromEntity(tag))))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Tag not found with id: " + id)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<TagDTO>>> getTagsByUserId(@PathVariable Long userId) {
        List<TagDTO> tags = tagService.getTagsByUserId(userId).stream()
                .map(TagDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(tags));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<TagDTO>>> searchTags(
            @RequestParam String query,
            @RequestParam(required = false) Long userId) {
        List<Tag> tags;
        if (userId != null) {
            tags = tagService.getTagsByNameContainingAndUserId(query, userId);
        } else {
            tags = tagService.getTagsByNameContaining(query);
        }

        List<TagDTO> tagDTOs = tags.stream()
                .map(TagDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(tagDTOs));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TagDTO>> createTag(
            @RequestBody TagDTO tagDTO,
            @RequestHeader(value = "X-Username", defaultValue = "system") String username) {
        Tag tag = tagDTO.toEntity();
        Tag createdTag = tagService.createTag(tag, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tag created successfully", TagDTO.fromEntity(createdTag)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TagDTO>> updateTag(
            @PathVariable Long id,
            @RequestBody TagDTO tagDTO,
            @RequestHeader(value = "X-Username", defaultValue = "system") String username) {
        Tag tag = tagDTO.toEntity();
        Tag updatedTag = tagService.updateTag(id, tag, username);

        if (updatedTag != null) {
            return ResponseEntity.ok(ApiResponse.success("Tag updated successfully", TagDTO.fromEntity(updatedTag)));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Tag not found with id: " + id));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<TagDTO>> deleteTag(
            @PathVariable Long id,
            @RequestHeader(value = "X-Username", defaultValue = "system") String username) {
        Tag deletedTag = tagService.deleteTag(id, username);

        if (deletedTag != null) {
            return ResponseEntity.ok(ApiResponse.success("Tag deleted successfully", TagDTO.fromEntity(deletedTag)));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Tag not found with id: " + id));
        }
    }

    @PostMapping("/undo")
    public ResponseEntity<ApiResponse<Void>> undoLastCommand(
            @RequestHeader(value = "X-Username", defaultValue = "system") String username) {
        boolean result = tagService.undoLastCommand(username);

        if (result) {
            return ResponseEntity.ok(ApiResponse.success("Last command undone successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to undo last command"));
        }
    }
}
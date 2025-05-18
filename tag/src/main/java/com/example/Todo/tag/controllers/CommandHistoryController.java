package com.example.Todo.tag.controllers;

import com.example.Todo.tag.dto.ApiResponse;
import com.example.Todo.tag.dto.CommandHistoryDTO;
import com.example.Todo.tag.services.CommandHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/command-history")
public class CommandHistoryController {

    private final CommandHistoryService commandHistoryService;

    @Autowired
    public CommandHistoryController(CommandHistoryService commandHistoryService) {
        this.commandHistoryService = commandHistoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CommandHistoryDTO>>> getAllCommandHistory() {
        List<CommandHistoryDTO> history = commandHistoryService.getAllCommandHistory().stream()
                .map(CommandHistoryDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(history));
    }

    @GetMapping("/type/{commandType}")
    public ResponseEntity<ApiResponse<List<CommandHistoryDTO>>> getCommandHistoryByType(
            @PathVariable String commandType) {
        List<CommandHistoryDTO> history = commandHistoryService.getCommandHistoryByType(commandType).stream()
                .map(CommandHistoryDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(history));
    }

    @GetMapping("/user/{executedBy}")
    public ResponseEntity<ApiResponse<List<CommandHistoryDTO>>> getCommandHistoryByUser(
            @PathVariable String executedBy) {
        List<CommandHistoryDTO> history = commandHistoryService.getCommandHistoryByUser(executedBy).stream()
                .map(CommandHistoryDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(history));
    }

    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<List<CommandHistoryDTO>>> getCommandHistoryByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
        List<CommandHistoryDTO> history = commandHistoryService.getCommandHistoryByDateRange(startDateTime, endDateTime)
                .stream()
                .map(CommandHistoryDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(history));
    }

    @GetMapping("/undo-status")
    public ResponseEntity<ApiResponse<List<CommandHistoryDTO>>> getCommandHistoryByUndoStatus(
            @RequestParam boolean isUndone) {
        List<CommandHistoryDTO> history = commandHistoryService.getCommandHistoryByUndoStatus(isUndone).stream()
                .map(CommandHistoryDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(history));
    }

    @PutMapping("/{id}/mark-undone")
    public ResponseEntity<ApiResponse<CommandHistoryDTO>> markCommandAsUndone(@PathVariable Long id) {
        return commandHistoryService.markCommandAsUndone(id)
                .map(commandHistory -> ResponseEntity.ok(ApiResponse.success(
                        "Command marked as undone successfully",
                        CommandHistoryDTO.fromEntity(commandHistory))))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Command history not found with id: " + id)));
    }
}
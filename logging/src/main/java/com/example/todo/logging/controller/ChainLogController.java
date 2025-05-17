package com.example.todo.logging.controller;

import com.example.todo.logging.model.Log;
import com.example.todo.logging.model.LogResponse;
import com.example.todo.logging.service.ChainLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for chain log operations
 */
@RestController
@RequestMapping("/api/logs/chain")
public class ChainLogController {

    private final ChainLogService chainLogService;

    public ChainLogController(ChainLogService chainLogService) {
        this.chainLogService = chainLogService;
    }

    /**
     * Create a new chain log
     * 
     * @param message Chain operation message
     * @param source  Source of the operation
     * @param userId  User ID (optional)
     * @return Created log
     */
    @PostMapping
    public ResponseEntity<Log> logChainOperation(
            @RequestParam String message,
            @RequestParam String source,
            @RequestParam(required = false) String userId) {

        Log log = chainLogService.logChainOperation(message, source, userId);
        return new ResponseEntity<>(log, HttpStatus.CREATED);
    }

    /**
     * Get all chain logs with pagination
     * 
     * @param page Page number (default 0)
     * @param size Page size (default 20)
     * @return Paginated chain logs
     */
    @GetMapping
    public ResponseEntity<LogResponse> getAllChainLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        LogResponse response = chainLogService.findAllChainLogs(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get chain logs by user ID
     * 
     * @param userId User ID
     * @param page   Page number (default 0)
     * @param size   Page size (default 20)
     * @return Paginated chain logs
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<LogResponse> getChainLogsByUserId(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        LogResponse response = chainLogService.findChainLogsByUserId(userId, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
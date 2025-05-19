package com.example.logging.controller;

import com.example.logging.model.Log;
import com.example.logging.model.LogResponse;
import com.example.logging.service.FilterLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for filter log operations
 */
@RestController
@RequestMapping("/api/logs/filter")
public class FilterLogController {

    private final FilterLogService filterLogService;

    public FilterLogController(FilterLogService filterLogService) {
        this.filterLogService = filterLogService;
    }

    /**
     * Create a new filter log
     * 
     * @param message Filter operation message
     * @param source  Source of the operation
     * @param userId  User ID (optional)
     * @return Created log
     */
    @PostMapping
    public ResponseEntity<Log> logFilterOperation(
            @RequestParam String message,
            @RequestParam String source,
            @RequestParam(required = false) String userId) {

        Log log = filterLogService.logFilterOperation(message, source, userId);
        return new ResponseEntity<>(log, HttpStatus.CREATED);
    }

    /**
     * Get all filter logs with pagination
     * 
     * @param page Page number (default 0)
     * @param size Page size (default 20)
     * @return Paginated filter logs
     */
    @GetMapping
    public ResponseEntity<LogResponse> getAllFilterLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        LogResponse response = filterLogService.findAllFilterLogs(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get filter logs by user ID
     * 
     * @param userId User ID
     * @param page   Page number (default 0)
     * @param size   Page size (default 20)
     * @return Paginated filter logs
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<LogResponse> getFilterLogsByUserId(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        LogResponse response = filterLogService.findFilterLogsByUserId(userId, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get filter logs by filter criteria
     * 
     * @param criteria Filter criteria text
     * @param page     Page number (default 0)
     * @param size     Page size (default 20)
     * @return Paginated filter logs
     */
    @GetMapping("/criteria")
    public ResponseEntity<LogResponse> getFilterLogsByCriteria(
            @RequestParam String criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        LogResponse response = filterLogService.findFilterLogsByFilterCriteria(criteria, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
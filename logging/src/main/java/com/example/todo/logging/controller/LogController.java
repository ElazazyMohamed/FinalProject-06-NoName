package com.example.todo.logging.controller;

import com.example.todo.logging.model.Log;
import com.example.todo.logging.model.LogFilter;
import com.example.todo.logging.model.LogLevel;
import com.example.todo.logging.model.LogRequest;
import com.example.todo.logging.model.LogResponse;
import com.example.todo.logging.model.LogType;
import com.example.todo.logging.service.LogService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * REST controller for logging operations
 */
@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    /**
     * Create a new log entry
     * 
     * @param request Log request
     * @return Created log
     */
    @PostMapping
    public ResponseEntity<Log> createLog(@RequestBody LogRequest request) {
        Log log = logService.createLog(request);
        return new ResponseEntity<>(log, HttpStatus.CREATED);
    }

    /**
     * Get a log by ID
     * 
     * @param id Log ID
     * @return Log if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Log> getLogById(@PathVariable String id) {
        Optional<Log> log = logService.findById(id);
        return log.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Get all logs with pagination
     * 
     * @param page      Page number (default 0)
     * @param size      Page size (default 20)
     * @param sortBy    Sort field (default timestamp)
     * @param ascending Sort direction (default false)
     * @return Paginated logs
     */
    @GetMapping
    public ResponseEntity<LogResponse> getAllLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "false") boolean ascending) {

        LogResponse response = logService.findAllLogs(page, size, sortBy, ascending);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Find logs by level
     * 
     * @param level Log level
     * @param page  Page number (default 0)
     * @param size  Page size (default 20)
     * @return Paginated logs
     */
    @GetMapping("/level/{level}")
    public ResponseEntity<LogResponse> getLogsByLevel(
            @PathVariable LogLevel level,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        LogResponse response = logService.findByLevel(level, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Find logs by type
     * 
     * @param type Log type
     * @param page Page number (default 0)
     * @param size Page size (default 20)
     * @return Paginated logs
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<LogResponse> getLogsByType(
            @PathVariable LogType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        LogResponse response = logService.findByType(type, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Find logs by user ID
     * 
     * @param userId User ID
     * @param page   Page number (default 0)
     * @param size   Page size (default 20)
     * @return Paginated logs
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<LogResponse> getLogsByUserId(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        LogResponse response = logService.findByUserId(userId, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Find logs in time range
     * 
     * @param start Start date/time
     * @param end   End date/time
     * @param page  Page number (default 0)
     * @param size  Page size (default 20)
     * @return Paginated logs
     */
    @GetMapping("/time-range")
    public ResponseEntity<LogResponse> getLogsByTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        LogResponse response = logService.findByTimeRange(start, end, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Search logs with complex filter criteria
     * 
     * @param filter Log filter criteria
     * @return Paginated logs
     */
    @PostMapping("/search")
    public ResponseEntity<LogResponse> searchLogs(@RequestBody LogFilter filter) {
        LogResponse response = logService.findLogsWithFilter(filter);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Delete logs older than specified days
     * 
     * @param days Number of days
     * @return Count of deleted logs
     */
    @DeleteMapping("/cleanup/{days}")
    public ResponseEntity<String> cleanupOldLogs(@PathVariable int days) {
        long count = logService.deleteLogsOlderThan(days);
        return new ResponseEntity<>("Deleted " + count + " logs older than " + days + " days", HttpStatus.OK);
    }

    /**
     * Get log statistics by source
     * 
     * @return Source statistics
     */
    @GetMapping("/stats/source")
    public ResponseEntity<Object[]> getSourceStats() {
        return new ResponseEntity<>(logService.getLogStatsBySource(), HttpStatus.OK);
    }

    /**
     * Get log statistics by type
     * 
     * @return Type statistics
     */
    @GetMapping("/stats/type")
    public ResponseEntity<Object[]> getTypeStats() {
        return new ResponseEntity<>(logService.getLogStatsByType(), HttpStatus.OK);
    }

    /**
     * Get log statistics by level
     * 
     * @return Level statistics
     */
    @GetMapping("/stats/level")
    public ResponseEntity<Object[]> getLevelStats() {
        return new ResponseEntity<>(logService.getLogStatsByLevel(), HttpStatus.OK);
    }
}
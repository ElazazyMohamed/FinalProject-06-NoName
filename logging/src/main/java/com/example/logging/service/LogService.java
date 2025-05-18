package com.example.logging.service;

import com.example.logging.model.Log;
import com.example.logging.model.LogFilter;
import com.example.logging.model.LogLevel;
import com.example.logging.model.LogRequest;
import com.example.logging.model.LogResponse;
import com.example.logging.model.LogType;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service interface for logging operations
 */
public interface LogService {

    /**
     * Create a new log entry
     * 
     * @param request The log request
     * @return The created log
     */
    Log createLog(LogRequest request);

    /**
     * Find a log by its ID
     * 
     * @param id The log ID
     * @return The log if found
     */
    Optional<Log> findById(String id);

    /**
     * Get all logs with pagination
     * 
     * @param page      Page number
     * @param size      Page size
     * @param sortBy    Sort field
     * @param ascending Sort direction
     * @return Paginated log response
     */
    LogResponse findAllLogs(int page, int size, String sortBy, boolean ascending);

    /**
     * Find logs with complex filter criteria
     * 
     * @param filter The filter criteria
     * @return Paginated log response
     */
    LogResponse findLogsWithFilter(LogFilter filter);

    /**
     * Find logs by level
     * 
     * @param level The log level
     * @param page  Page number
     * @param size  Page size
     * @return Paginated log response
     */
    LogResponse findByLevel(LogLevel level, int page, int size);

    /**
     * Find logs by type
     * 
     * @param type The log type
     * @param page Page number
     * @param size Page size
     * @return Paginated log response
     */
    LogResponse findByType(LogType type, int page, int size);

    /**
     * Find logs by user ID
     * 
     * @param userId The user ID
     * @param page   Page number
     * @param size   Page size
     * @return Paginated log response
     */
    LogResponse findByUserId(String userId, int page, int size);

    /**
     * Find logs by time range
     * 
     * @param start Start date
     * @param end   End date
     * @param page  Page number
     * @param size  Page size
     * @return Paginated log response
     */
    LogResponse findByTimeRange(LocalDateTime start, LocalDateTime end, int page, int size);

    /**
     * Delete logs older than specified days
     * 
     * @param days Number of days
     * @return Number of deleted logs
     */
    long deleteLogsOlderThan(int days);

    /**
     * Get log statistics by source
     * 
     * @return Array of source statistics
     */
    Object[] getLogStatsBySource();

    /**
     * Get log statistics by type
     * 
     * @return Array of type statistics
     */
    Object[] getLogStatsByType();

    /**
     * Get log statistics by level
     * 
     * @return Array of level statistics
     */
    Object[] getLogStatsByLevel();
}
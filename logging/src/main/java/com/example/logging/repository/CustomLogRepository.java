package com.example.logging.repository;

import com.example.logging.model.Log;
import com.example.logging.model.LogFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Custom repository interface for more complex log queries
 */
public interface CustomLogRepository {

    /**
     * Find logs using complex filtering criteria
     * 
     * @param filter   The filter criteria
     * @param pageable Pagination information
     * @return A page of logs matching the criteria
     */
    Page<Log> findLogsWithFilter(LogFilter filter, Pageable pageable);

    /**
     * Delete logs older than a specified number of days
     * 
     * @param days Number of days
     * @return Number of deleted logs
     */
    long deleteLogsOlderThan(int days);

    /**
     * Count logs by source
     * 
     * @return Array of source names and counts
     */
    Object[] countLogsBySource();

    /**
     * Count logs by type
     * 
     * @return Array of type names and counts
     */
    Object[] countLogsByType();

    /**
     * Count logs by level
     * 
     * @return Array of level names and counts
     */
    Object[] countLogsByLevel();
}
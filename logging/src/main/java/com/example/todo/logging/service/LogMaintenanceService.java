package com.example.todo.logging.service;

import com.example.todo.logging.repository.LogRepositoryCustom;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Service for log maintenance and cleanup operations
 */
@Service
public class LogMaintenanceService {

    private final LogRepositoryCustom logRepository;

    // Default retention period in days
    private static final int DEFAULT_RETENTION_DAYS = 30;

    public LogMaintenanceService(LogRepositoryCustom logRepository) {
        this.logRepository = logRepository;
    }

    /**
     * Delete logs older than the specified number of days
     * 
     * @param days Retention period in days
     * @return Number of deleted logs
     */
    public long cleanupOldLogs(int days) {
        return logRepository.deleteLogsOlderThan(days);
    }

    /**
     * Scheduled task to clean up old logs
     * Runs daily at 2:00 AM
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void scheduledCleanup() {
        logRepository.deleteLogsOlderThan(DEFAULT_RETENTION_DAYS);
    }

    /**
     * Get statistics about log distribution by source
     * 
     * @return Array of source statistics
     */
    public Object[] getSourceStatistics() {
        return logRepository.countLogsBySource();
    }

    /**
     * Get statistics about log distribution by type
     * 
     * @return Array of type statistics
     */
    public Object[] getTypeStatistics() {
        return logRepository.countLogsByType();
    }

    /**
     * Get statistics about log distribution by level
     * 
     * @return Array of level statistics
     */
    public Object[] getLevelStatistics() {
        return logRepository.countLogsByLevel();
    }
}
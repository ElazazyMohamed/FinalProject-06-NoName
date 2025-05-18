package com.example.logging.controller;

import com.example.logging.service.LogMaintenanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for log maintenance operations
 */
@RestController
@RequestMapping("/api/logs/maintenance")
public class LogMaintenanceController {

    private final LogMaintenanceService maintenanceService;

    public LogMaintenanceController(LogMaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    /**
     * Clean up logs older than specified days
     * 
     * @param days Number of days
     * @return Result message with count of deleted logs
     */
    @DeleteMapping("/cleanup/{days}")
    public ResponseEntity<String> cleanupOldLogs(@PathVariable int days) {
        long count = maintenanceService.cleanupOldLogs(days);
        return new ResponseEntity<>("Deleted " + count + " logs older than " + days + " days", HttpStatus.OK);
    }

    /**
     * Get log statistics by source
     * 
     * @return Source statistics
     */
    @GetMapping("/stats/source")
    public ResponseEntity<Object[]> getSourceStats() {
        return new ResponseEntity<>(maintenanceService.getSourceStatistics(), HttpStatus.OK);
    }

    /**
     * Get log statistics by type
     * 
     * @return Type statistics
     */
    @GetMapping("/stats/type")
    public ResponseEntity<Object[]> getTypeStats() {
        return new ResponseEntity<>(maintenanceService.getTypeStatistics(), HttpStatus.OK);
    }

    /**
     * Get log statistics by level
     * 
     * @return Level statistics
     */
    @GetMapping("/stats/level")
    public ResponseEntity<Object[]> getLevelStats() {
        return new ResponseEntity<>(maintenanceService.getLevelStatistics(), HttpStatus.OK);
    }
}
package com.example.todo.logging.repository;

import com.example.todo.logging.model.Log;
import com.example.todo.logging.model.LogLevel;
import com.example.todo.logging.model.LogType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Log entities
 */
@Repository
public interface LogRepository extends MongoRepository<Log, String> {

    // Find logs by level
    List<Log> findByLevel(LogLevel level);

    // Find logs by type
    List<Log> findByType(LogType type);

    // Find logs by user ID
    List<Log> findByUserId(String userId);

    // Find logs by source
    List<Log> findBySource(String source);

    // Find logs by message containing text (case insensitive)
    List<Log> findByMessageContainingIgnoreCase(String message);

    // Find logs created between two timestamps
    List<Log> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    // Find logs by level with pagination
    Page<Log> findByLevel(LogLevel level, Pageable pageable);

    // Find logs by type with pagination
    Page<Log> findByType(LogType type, Pageable pageable);

    // Find logs by user ID with pagination
    Page<Log> findByUserId(String userId, Pageable pageable);

    // Find logs by level and type
    List<Log> findByLevelAndType(LogLevel level, LogType type);

    // Find logs by level and type with pagination
    Page<Log> findByLevelAndType(LogLevel level, LogType type, Pageable pageable);

    // Find logs by source containing text (case insensitive)
    List<Log> findBySourceContainingIgnoreCase(String source);

    // Complex query with pagination
    Page<Log> findByLevelInAndTypeInAndTimestampBetween(
            List<LogLevel> levels,
            List<LogType> types,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable);
}
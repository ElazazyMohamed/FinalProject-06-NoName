package com.example.todo.logging.repository;

/**
 * Combined repository interface that extends both basic and custom repositories
 */
public interface LogRepositoryCustom extends LogRepository, CustomLogRepository {
    // This interface combines functionality from both parent interfaces
    // No additional methods needed here
}
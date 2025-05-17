package com.example.todo.logging.model;

/**
 * Enum representing different types of logs based on functionality from the
 * project design
 */
public enum LogType {
    CHAIN_LOG, // For Chain Logs
    FILTER_LOG, // For Filter Logs
    LOGGER_PAGE, // For Logger Page functionality
    DIFFERENT_LOG, // For different log levels
    USER_ACTION, // For logs of user actions
    SYSTEM_EVENT, // For system events
    API_CALL // For API call logs
}
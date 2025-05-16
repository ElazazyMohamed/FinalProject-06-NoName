package com.example.todo.logging.model;

/**
 * Factory pattern implementation for creating different types of logs
 */
public class LogFactory {

    /**
     * Creates a new log with specified parameters
     * 
     * @param message Log message
     * @param level   Log level
     * @param type    Log type
     * @param source  Source of the log
     * @return A new Log instance
     */
    public static Log createLog(String message, LogLevel level, LogType type, String source) {
        return new Log(message, level, type, source);
    }

    /**
     * Creates a new log with all parameters
     * 
     * @param message Log message
     * @param level   Log level
     * @param type    Log type
     * @param source  Source of the log
     * @param userId  User ID associated with the log
     * @param payload Additional payload data
     * @return A new Log instance
     */
    public static Log createLog(String message, LogLevel level, LogType type, String source, String userId,
            Object payload) {
        return new Log(message, level, type, source, userId, payload);
    }

    /**
     * Creates a new chain log
     * 
     * @param message Log message
     * @param source  Source of the log
     * @param userId  User ID associated with the log (optional)
     * @return A new Log instance with CHAIN_LOG type
     */
    public static Log createChainLog(String message, String source, String userId) {
        Log log = new Log(message, LogLevel.INFO, LogType.CHAIN_LOG, source);
        if (userId != null) {
            log.setUserId(userId);
        }
        return log;
    }

    /**
     * Creates a new filter log
     * 
     * @param message Log message
     * @param source  Source of the log
     * @param userId  User ID associated with the log (optional)
     * @return A new Log instance with FILTER_LOG type
     */
    public static Log createFilterLog(String message, String source, String userId) {
        Log log = new Log(message, LogLevel.INFO, LogType.FILTER_LOG, source);
        if (userId != null) {
            log.setUserId(userId);
        }
        return log;
    }

    /**
     * Creates a new error log
     * 
     * @param message Error message
     * @param source  Source of the error
     * @param error   The error object
     * @return A new Log instance with ERROR level
     */
    public static Log createErrorLog(String message, String source, Throwable error) {
        Log log = new Log(message, LogLevel.ERROR, LogType.SYSTEM_EVENT, source);
        log.setPayload(error);
        return log;
    }

    /**
     * Creates a new user action log
     * 
     * @param message    Action description
     * @param source     Source of the action
     * @param userId     User ID performing the action
     * @param actionData Additional action data
     * @return A new Log instance with USER_ACTION type
     */
    public static Log createUserActionLog(String message, String source, String userId, Object actionData) {
        return new Log(message, LogLevel.INFO, LogType.USER_ACTION, source, userId, actionData);
    }
}
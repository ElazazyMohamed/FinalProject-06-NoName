package com.example.logging.service;

import com.example.logging.model.Log;
import com.example.logging.model.LogFactory;
import com.example.logging.model.LogFilter;
import com.example.logging.model.LogLevel;
import com.example.logging.model.LogRequest;
import com.example.logging.model.LogResponse;
import com.example.logging.model.LogType;
import com.example.logging.rabbitmq.RabbitMQConfig;
import com.example.logging.repository.LogRepositoryCustom;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Implementation of the LogService interface
 */
@Service
public class LogServiceImpl implements LogService {

    private final LogRepositoryCustom logRepository;

    public LogServiceImpl(LogRepositoryCustom logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public Log createLog(LogRequest request) {
        Log log = LogFactory.createLog(
                request.getMessage(),
                request.getLevel(),
                request.getType(),
                request.getSource(),
                request.getUserId(),
                request.getPayload());
        return logRepository.save(log);
    }

    @Override
    public Optional<Log> findById(String id) {
        return logRepository.findById(id);
    }

    @Override
    public LogResponse findAllLogs(int page, int size, String sortBy, boolean ascending) {
        Sort sort = Sort.by(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Log> logPage = logRepository.findAll(pageable);

        return new LogResponse(
                logPage.getContent(),
                logPage.getTotalElements(),
                logPage.getTotalPages(),
                page,
                size);
    }

    @Override
    public LogResponse findLogsWithFilter(LogFilter filter) {
        Pageable pageable = PageRequest.of(
                filter.getPage(),
                filter.getSize(),
                filter.getAscending() ? Sort.Direction.ASC : Sort.Direction.DESC,
                filter.getSortBy());

        Page<Log> logPage = logRepository.findLogsWithFilter(filter, pageable);

        return new LogResponse(
                logPage.getContent(),
                logPage.getTotalElements(),
                logPage.getTotalPages(),
                filter.getPage(),
                filter.getSize());
    }

    @Override
    public LogResponse findByLevel(LogLevel level, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        Page<Log> logPage = logRepository.findByLevel(level, pageable);

        return new LogResponse(
                logPage.getContent(),
                logPage.getTotalElements(),
                logPage.getTotalPages(),
                page,
                size);
    }

    @Override
    public LogResponse findByType(LogType type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        Page<Log> logPage = logRepository.findByType(type, pageable);

        return new LogResponse(
                logPage.getContent(),
                logPage.getTotalElements(),
                logPage.getTotalPages(),
                page,
                size);
    }

    @Override
    public LogResponse findByUserId(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        Page<Log> logPage = logRepository.findByUserId(userId, pageable);

        return new LogResponse(
                logPage.getContent(),
                logPage.getTotalElements(),
                logPage.getTotalPages(),
                page,
                size);
    }

    @Override
    public LogResponse findByTimeRange(LocalDateTime start, LocalDateTime end, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));

        // Create a filter to use with the custom repository method
        LogFilter filter = new LogFilter();
        filter.setStartDate(start);
        filter.setEndDate(end);
        filter.setPage(page);
        filter.setSize(size);
        filter.setSortBy("timestamp");
        filter.setAscending(false);

        Page<Log> logPage = logRepository.findLogsWithFilter(filter, pageable);

        return new LogResponse(
                logPage.getContent(),
                logPage.getTotalElements(),
                logPage.getTotalPages(),
                page,
                size);
    }

    @Override
    public long deleteLogsOlderThan(int days) {
        return logRepository.deleteLogsOlderThan(days);
    }

    @Override
    public Object[] getLogStatsBySource() {
        return logRepository.countLogsBySource();
    }

    @Override
    public Object[] getLogStatsByType() {
        return logRepository.countLogsByType();
    }

    @Override
    public Object[] getLogStatsByLevel() {
        return logRepository.countLogsByLevel();
    }

//    @RabbitListener(queues = RabbitMQConfig.LOGGING_QUEUE)
//    public void userEventsLog(Integer id) {
//        System.out.println("User with " + id + " Created");
//        Log log = LogFactory.createUserActionLog(
//                "User with " + id + " Created",
//                "User Service",
//                id.toString(),
//                LogLevel.INFO
//        );
//        logRepository.save(log);
//    }
//
//    @RabbitListener(queues = RabbitMQConfig.LOGGING_QUEUE)
//    public void notesEventsLog(String id) {
//        System.out.println("Note with " + id + " Created");
//        Log log = LogFactory.createUserActionLog(
//                "Note with " + id + " Created",
//                "Notes Service",
//                id.toString(),
//                LogLevel.INFO
//        );
//        logRepository.save(log);
//    }

    @RabbitListener(queues = RabbitMQConfig.LOGGING_QUEUE)
    public void handleLogEvents(String message) {
        System.out.println("Received message: " + message);

        String entity = extractValue(message, "entity");
        String source = extractValue(message, "source");
        String id = extractValue(message, "id");

        String logSource = switch (source) {
            case "user-service" -> "User Service";
            case "notes-service" -> "Notes Service";
            default -> "Unknown Source";
        };

        Log log = LogFactory.createUserActionLog(
                message,
                logSource,
                id,
                LogLevel.INFO
        );

        logRepository.save(log);
    }

    private String extractValue(String message, String key) {
        for (String part : message.split(";")) {
            part = part.trim();
            if (part.startsWith(key + "=")) {
                return part.substring((key + "=").length());
            }
        }
        return null;
    }
}
package com.example.logging.service;

import com.example.logging.model.Log;
import com.example.logging.model.LogFactory;
import com.example.logging.model.LogFilter;
import com.example.logging.model.LogResponse;
import com.example.logging.model.LogType;
import com.example.logging.repository.LogRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Specialized service for filter logs
 */
@Service
public class FilterLogService {

    private final LogRepositoryCustom logRepository;

    public FilterLogService(LogRepositoryCustom logRepository) {
        this.logRepository = logRepository;
    }

    /**
     * Log a filter operation
     * 
     * @param message Filter operation message
     * @param source  Source of the operation
     * @param userId  User ID (optional)
     * @return The created log
     */
    public Log logFilterOperation(String message, String source, String userId) {
        Log log = LogFactory.createFilterLog(message, source, userId);
        return logRepository.save(log);
    }

    /**
     * Find all filter logs with pagination
     * 
     * @param page Page number
     * @param size Page size
     * @return Paginated filter logs
     */
    public LogResponse findAllFilterLogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));

        LogFilter filter = new LogFilter();
        filter.setTypes(Collections.singletonList(LogType.FILTER_LOG));
        filter.setPage(page);
        filter.setSize(size);

        Page<Log> logPage = logRepository.findLogsWithFilter(filter, pageable);

        return new LogResponse(
                logPage.getContent(),
                logPage.getTotalElements(),
                logPage.getTotalPages(),
                page,
                size);
    }

    /**
     * Find filter logs by user ID
     * 
     * @param userId User ID
     * @param page   Page number
     * @param size   Page size
     * @return Paginated filter logs for the user
     */
    public LogResponse findFilterLogsByUserId(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));

        LogFilter filter = new LogFilter();
        filter.setTypes(Collections.singletonList(LogType.FILTER_LOG));
        filter.setUserId(userId);
        filter.setPage(page);
        filter.setSize(size);

        Page<Log> logPage = logRepository.findLogsWithFilter(filter, pageable);

        return new LogResponse(
                logPage.getContent(),
                logPage.getTotalElements(),
                logPage.getTotalPages(),
                page,
                size);
    }

    /**
     * Find filter logs by filter criteria
     * 
     * @param filterCriteria Filter criteria text
     * @param page           Page number
     * @param size           Page size
     * @return Paginated filter logs matching the criteria
     */
    public LogResponse findFilterLogsByFilterCriteria(String filterCriteria, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));

        LogFilter filter = new LogFilter();
        filter.setTypes(Collections.singletonList(LogType.FILTER_LOG));
        filter.setMessageContains(filterCriteria);
        filter.setPage(page);
        filter.setSize(size);

        Page<Log> logPage = logRepository.findLogsWithFilter(filter, pageable);

        return new LogResponse(
                logPage.getContent(),
                logPage.getTotalElements(),
                logPage.getTotalPages(),
                page,
                size);
    }
}
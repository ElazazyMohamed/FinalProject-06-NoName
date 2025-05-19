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
 * Specialized service for chain logs
 */
@Service
public class ChainLogService {

    private final LogRepositoryCustom logRepository;

    public ChainLogService(LogRepositoryCustom logRepository) {
        this.logRepository = logRepository;
    }

    /**
     * Log a chain operation
     * 
     * @param message Chain operation message
     * @param source  Source of the operation
     * @param userId  User ID (optional)
     * @return The created log
     */
    public Log logChainOperation(String message, String source, String userId) {
        Log log = LogFactory.createChainLog(message, source, userId);
        return logRepository.save(log);
    }

    /**
     * Find all chain logs with pagination
     * 
     * @param page Page number
     * @param size Page size
     * @return Paginated chain logs
     */
    public LogResponse findAllChainLogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));

        LogFilter filter = new LogFilter();
        filter.setTypes(Collections.singletonList(LogType.CHAIN_LOG));
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
     * Find chain logs by user ID
     * 
     * @param userId User ID
     * @param page   Page number
     * @param size   Page size
     * @return Paginated chain logs for the user
     */
    public LogResponse findChainLogsByUserId(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));

        LogFilter filter = new LogFilter();
        filter.setTypes(Collections.singletonList(LogType.CHAIN_LOG));
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
}
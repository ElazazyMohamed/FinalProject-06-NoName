package com.example.Todo.tag.services;

import com.example.Todo.tag.models.CommandHistory;
import com.example.Todo.tag.repositories.CommandHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing command history entries
 */
@Service
public class CommandHistoryService {

    private final CommandHistoryRepository commandHistoryRepository;

    @Autowired
    public CommandHistoryService(CommandHistoryRepository commandHistoryRepository) {
        this.commandHistoryRepository = commandHistoryRepository;
    }

    /**
     * Log a command execution
     * 
     * @param commandType    the type of command (CREATE_TAG, UPDATE_TAG, etc.)
     * @param commandDetails details about the command execution
     * @param executedBy     the username of the user who executed the command
     * @param resultData     data about the result of the command execution
     * @return the created command history entry
     */
    @Transactional
    public CommandHistory logCommand(String commandType, String commandDetails, String executedBy, String resultData) {
        CommandHistory commandHistory = new CommandHistory(commandType, commandDetails, executedBy, resultData);
        return commandHistoryRepository.save(commandHistory);
    }

    /**
     * Log an undo action
     * 
     * @param executedBy the username of the user who performed the undo
     * @return the created command history entry
     */
    @Transactional
    public CommandHistory logUndo(String executedBy) {
        return logCommand("UNDO", "Undid last command", executedBy, null);
    }

    /**
     * Mark a command as undone
     * 
     * @param id the ID of the command history entry
     * @return the updated command history entry if found, empty otherwise
     */
    @Transactional
    public Optional<CommandHistory> markCommandAsUndone(Long id) {
        Optional<CommandHistory> optionalCommandHistory = commandHistoryRepository.findById(id);
        if (optionalCommandHistory.isPresent()) {
            CommandHistory commandHistory = optionalCommandHistory.get();
            commandHistory.setUndone(true);
            return Optional.of(commandHistoryRepository.save(commandHistory));
        }
        return Optional.empty();
    }

    /**
     * Get all command history entries
     * 
     * @return list of all command history entries
     */
    public List<CommandHistory> getAllCommandHistory() {
        return commandHistoryRepository.findAll();
    }

    /**
     * Get command history entries by command type
     * 
     * @param commandType the type of command
     * @return list of command history entries of the specified type
     */
    public List<CommandHistory> getCommandHistoryByType(String commandType) {
        return commandHistoryRepository.findByCommandType(commandType);
    }

    /**
     * Get command history entries executed by a specific user
     * 
     * @param executedBy the username of the user
     * @return list of command history entries executed by the specified user
     */
    public List<CommandHistory> getCommandHistoryByUser(String executedBy) {
        return commandHistoryRepository.findByExecutedBy(executedBy);
    }

    /**
     * Get command history entries executed within a date range
     * 
     * @param startDateTime the start date/time
     * @param endDateTime   the end date/time
     * @return list of command history entries executed within the specified range
     */
    public List<CommandHistory> getCommandHistoryByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return commandHistoryRepository.findByExecutedAtBetween(startDateTime, endDateTime);
    }

    /**
     * Get command history entries by undo status
     * 
     * @param isUndone true for undone commands, false for commands that haven't
     *                 been undone
     * @return list of command history entries based on their undo status
     */
    public List<CommandHistory> getCommandHistoryByUndoStatus(boolean isUndone) {
        return commandHistoryRepository.findByIsUndone(isUndone);
    }
}
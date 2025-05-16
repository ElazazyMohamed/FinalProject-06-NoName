package com.example.Todo.tag.repositories;

import com.example.Todo.tag.models.CommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for CommandHistory entities
 */
@Repository
public interface CommandHistoryRepository extends JpaRepository<CommandHistory, Long> {

    /**
     * Find command history by command type
     * 
     * @param commandType the type of command
     * @return list of command history entries of the specified type
     */
    List<CommandHistory> findByCommandType(String commandType);

    /**
     * Find command history by who executed it
     * 
     * @param executedBy the user who executed the command
     * @return list of command history entries executed by the specified user
     */
    List<CommandHistory> findByExecutedBy(String executedBy);

    /**
     * Find command history by executed date range
     * 
     * @param startDateTime the start date/time
     * @param endDateTime   the end date/time
     * @return list of command history entries executed within the specified range
     */
    List<CommandHistory> findByExecutedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    /**
     * Find command history by whether it was undone
     * 
     * @param isUndone true for undone commands, false for commands that haven't
     *                 been undone
     * @return list of command history entries based on their undo status
     */
    List<CommandHistory> findByIsUndone(boolean isUndone);

    /**
     * Find command history by command type and executed by
     * 
     * @param commandType the type of command
     * @param executedBy  the user who executed the command
     * @return list of command history entries of the specified type and executed by
     *         the specified user
     */
    List<CommandHistory> findByCommandTypeAndExecutedBy(String commandType, String executedBy);
}

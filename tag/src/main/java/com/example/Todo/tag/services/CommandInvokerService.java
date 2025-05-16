package com.example.Todo.tag.services;

import com.example.Todo.tag.models.commands.Command;
import com.example.Todo.tag.models.commands.CommandInvoker;
import org.springframework.stereotype.Service;

/**
 * Service wrapper for the CommandInvoker
 */
@Service
public class CommandInvokerService {

    private final CommandInvoker commandInvoker;

    public CommandInvokerService() {
        this.commandInvoker = new CommandInvoker();
    }

    /**
     * Execute a command
     * 
     * @param command the command to execute
     * @return the result of the command execution
     */
    public Object executeCommand(Command command) {
        return commandInvoker.executeCommand(command);
    }

    /**
     * Undo the last executed command
     * 
     * @return true if the undo was successful, false otherwise
     */
    public boolean undoLastCommand() {
        return commandInvoker.undoLastCommand();
    }

    /**
     * Get the number of commands that can be undone
     * 
     * @return the number of commands in the undo stack
     */
    public int getUndoStackSize() {
        return commandInvoker.getUndoStackSize();
    }

    /**
     * Clear the undo stack
     */
    public void clearUndoStack() {
        commandInvoker.clearUndoStack();
    }

    /**
     * Get the CommandInvoker instance
     * 
     * @return the CommandInvoker instance
     */
    public CommandInvoker getCommandInvoker() {
        return commandInvoker;
    }
}
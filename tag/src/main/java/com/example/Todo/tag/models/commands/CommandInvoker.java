package com.example.Todo.tag.models.commands;

import java.util.Stack;

/**
 * Invoker class for executing commands and managing undo operations
 */
public class CommandInvoker {

    private final Stack<Command> executedCommands = new Stack<>();

    /**
     * Execute a command and store it for potential undo
     * 
     * @param command the command to execute
     * @return the result of the command execution
     */
    public Object executeCommand(Command command) {
        Object result = command.execute();
        executedCommands.push(command);
        return result;
    }

    /**
     * Undo the last executed command
     * 
     * @return true if the undo was successful, false otherwise
     */
    public boolean undoLastCommand() {
        if (!executedCommands.isEmpty()) {
            Command lastCommand = executedCommands.pop();
            return lastCommand.undo();
        }
        return false;
    }

    /**
     * Get the number of commands that can be undone
     * 
     * @return the number of commands in the undo stack
     */
    public int getUndoStackSize() {
        return executedCommands.size();
    }

    /**
     * Clear the undo stack
     */
    public void clearUndoStack() {
        executedCommands.clear();
    }
}
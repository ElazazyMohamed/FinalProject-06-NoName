package com.example.Todo.tag.models.commands;

/**
 * The Command interface that all tag command implementations must implement.
 */
public interface Command {

    /**
     * Execute the command.
     * 
     * @return Object result of the command execution
     */
    Object execute();

    /**
     * Undo the command if possible.
     * 
     * @return true if undo was successful, false otherwise
     */
    boolean undo();
}
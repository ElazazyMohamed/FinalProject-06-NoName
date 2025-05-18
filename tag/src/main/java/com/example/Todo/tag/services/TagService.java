package com.example.Todo.tag.services;

import com.example.Todo.tag.models.Tag;
import com.example.Todo.tag.models.commands.*;
import com.example.Todo.tag.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing Tag entities using Command Pattern
 */
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final CommandInvokerService commandInvokerService;
    private final CommandHistoryService commandHistoryService;

    @Autowired
    public TagService(TagRepository tagRepository, CommandInvokerService commandInvokerService,
            CommandHistoryService commandHistoryService) {
        this.tagRepository = tagRepository;
        this.commandInvokerService = commandInvokerService;
        this.commandHistoryService = commandHistoryService;
    }

    /**
     * Get all tags
     * 
     * @return list of all tags
     */
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    /**
     * Get a tag by ID
     * 
     * @param id the ID of the tag
     * @return the tag if found, empty otherwise
     */
    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    /**
     * Get tags by user ID
     * 
     * @param userId the ID of the user
     * @return list of tags belonging to the user
     */
    public List<Tag> getTagsByUserId(Long userId) {
        return tagRepository.findByUserId(userId);
    }

    /**
     * Get tags containing the given text in their name
     * 
     * @param nameContains text to search for in tag names
     * @return list of tags with names containing the specified text
     */
    public List<Tag> getTagsByNameContaining(String nameContains) {
        return tagRepository.findByNameContainingIgnoreCase(nameContains);
    }

    /**
     * Get tags containing the given text in their name for a specific user
     * 
     * @param nameContains text to search for in tag names
     * @param userId       the ID of the user
     * @return list of tags with names containing the specified text and belonging
     *         to the user
     */
    public List<Tag> getTagsByNameContainingAndUserId(String nameContains, Long userId) {
        return tagRepository.findByNameContainingIgnoreCaseAndUserId(nameContains, userId);
    }

    /**
     * Create a new tag using the Command Pattern
     * 
     * @param tag      the tag to create
     * @param username the username of the user creating the tag
     * @return the created tag
     */
    @Transactional
    public Tag createTag(Tag tag, String username) {
        CreateTagCommand command = new CreateTagCommand(tagRepository, tag);
        Tag createdTag = (Tag) commandInvokerService.executeCommand(command);

        // Log the command execution
        commandHistoryService.logCommand("CREATE_TAG",
                "Created tag: " + tag.getName(),
                username,
                createdTag.getId().toString());

        return createdTag;
    }

    /**
     * Update an existing tag using the Command Pattern
     * 
     * @param id         the ID of the tag to update
     * @param updatedTag the updated tag data
     * @param username   the username of the user updating the tag
     * @return the updated tag if found, null otherwise
     */
    @Transactional
    public Tag updateTag(Long id, Tag updatedTag, String username) {
        UpdateTagCommand command = new UpdateTagCommand(tagRepository, id, updatedTag);
        Tag updated = (Tag) commandInvokerService.executeCommand(command);

        if (updated != null) {
            // Log the command execution
            commandHistoryService.logCommand("UPDATE_TAG",
                    "Updated tag: " + updated.getName(),
                    username,
                    updated.getId().toString());
        }

        return updated;
    }

    /**
     * Delete a tag using the Command Pattern
     * 
     * @param id       the ID of the tag to delete
     * @param username the username of the user deleting the tag
     * @return the deleted tag if found, null otherwise
     */
    @Transactional
    public Tag deleteTag(Long id, String username) {
        DeleteTagCommand command = new DeleteTagCommand(tagRepository, id);
        Tag deleted = (Tag) commandInvokerService.executeCommand(command);

        if (deleted != null) {
            // Log the command execution
            commandHistoryService.logCommand("DELETE_TAG",
                    "Deleted tag: " + deleted.getName(),
                    username,
                    deleted.getId().toString());
        }

        return deleted;
    }

    /**
     * Undo the last command
     * 
     * @param username the username of the user undoing the command
     * @return true if the undo was successful, false otherwise
     */
    @Transactional
    public boolean undoLastCommand(String username) {
        boolean result = commandInvokerService.undoLastCommand();

        if (result) {
            // Log the undo action
            commandHistoryService.logUndo(username);
        }

        return result;
    }
}
package com.example.Todo.tag.services;

import com.example.Todo.tag.models.TagNote;
import com.example.Todo.tag.models.commands.LinkTagToNoteCommand;
import com.example.Todo.tag.repositories.TagNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing TagNote entities using Command Pattern
 */
@Service
public class TagNoteService {

    private final TagNoteRepository tagNoteRepository;
    private final CommandInvokerService commandInvokerService;
    private final CommandHistoryService commandHistoryService;

    @Autowired
    public TagNoteService(TagNoteRepository tagNoteRepository, CommandInvokerService commandInvokerService,
            CommandHistoryService commandHistoryService) {
        this.tagNoteRepository = tagNoteRepository;
        this.commandInvokerService = commandInvokerService;
        this.commandHistoryService = commandHistoryService;
    }

    /**
     * Get all tag-note associations
     * 
     * @return list of all tag-note associations
     */
    public List<TagNote> getAllTagNotes() {
        return tagNoteRepository.findAll();
    }

    /**
     * Get a tag-note association by ID
     * 
     * @param id the ID of the tag-note association
     * @return the tag-note association if found, empty otherwise
     */
    public Optional<TagNote> getTagNoteById(Long id) {
        return tagNoteRepository.findById(id);
    }

    /**
     * Get tag-note associations by tag ID
     * 
     * @param tagId the ID of the tag
     * @return list of tag-note associations for the tag
     */
    public List<TagNote> getTagNotesByTagId(Long tagId) {
        return tagNoteRepository.findByTagId(tagId);
    }

    /**
     * Get tag-note associations by note ID
     * 
     * @param noteId the ID of the note
     * @return list of tag-note associations for the note
     */
    public List<TagNote> getTagNotesByNoteId(String noteId) {
        return tagNoteRepository.findByNoteId(noteId);
    }

    /**
     * Link a tag to a note using the Command Pattern
     * 
     * @param tagId    the ID of the tag
     * @param noteId   the ID of the note
     * @param username the username of the user creating the link
     * @return the created tag-note association
     */
    @Transactional
    public TagNote linkTagToNote(Long tagId, String noteId, String username) {
        // Check if link already exists
        TagNote existingLink = tagNoteRepository.findByTagIdAndNoteId(tagId, noteId);
        if (existingLink != null) {
            return existingLink;
        }

        LinkTagToNoteCommand command = new LinkTagToNoteCommand(tagNoteRepository, tagId, noteId);
        TagNote tagNote = (TagNote) commandInvokerService.executeCommand(command);

        // Log the command execution
        commandHistoryService.logCommand("LINK_TAG_TO_NOTE",
                "Linked tag " + tagId + " to note " + noteId,
                username,
                tagNote.getId().toString());

        return tagNote;
    }

    /**
     * Unlink a tag from a note
     * 
     * @param tagId    the ID of the tag
     * @param noteId   the ID of the note
     * @param username the username of the user removing the link
     * @return true if the link was removed, false otherwise
     */
    @Transactional
    public boolean unlinkTagFromNote(Long tagId, String noteId, String username) {
        // Check if link exists
        TagNote existingLink = tagNoteRepository.findByTagIdAndNoteId(tagId, noteId);
        if (existingLink == null) {
            return false;
        }

        Long linkId = existingLink.getId();
        tagNoteRepository.deleteByTagIdAndNoteId(tagId, noteId);

        // Log the command execution
        commandHistoryService.logCommand("UNLINK_TAG_FROM_NOTE",
                "Unlinked tag " + tagId + " from note " + noteId,
                username,
                linkId.toString());

        return true;
    }

    /**
     * Remove all links for a specific tag
     * 
     * @param tagId    the ID of the tag
     * @param username the username of the user removing the links
     * @return true if links were removed, false otherwise
     */
    @Transactional
    public boolean removeAllLinksForTag(Long tagId, String username) {
        List<TagNote> links = tagNoteRepository.findByTagId(tagId);
        if (links.isEmpty()) {
            return false;
        }

        tagNoteRepository.deleteByTagId(tagId);

        // Log the command execution
        commandHistoryService.logCommand("REMOVE_ALL_TAG_LINKS",
                "Removed all links for tag " + tagId,
                username,
                "Count: " + links.size());

        return true;
    }

    /**
     * Remove all links for a specific note
     * 
     * @param noteId   the ID of the note
     * @param username the username of the user removing the links
     * @return true if links were removed, false otherwise
     */
    @Transactional
    public boolean removeAllLinksForNote(String noteId, String username) {
        List<TagNote> links = tagNoteRepository.findByNoteId(noteId);
        if (links.isEmpty()) {
            return false;
        }

        tagNoteRepository.deleteByNoteId(noteId);

        // Log the command execution
        commandHistoryService.logCommand("REMOVE_ALL_NOTE_LINKS",
                "Removed all links for note " + noteId,
                username,
                "Count: " + links.size());

        return true;
    }
}
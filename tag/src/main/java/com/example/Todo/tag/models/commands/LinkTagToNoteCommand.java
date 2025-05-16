package com.example.Todo.tag.models.commands;

import com.example.Todo.tag.models.TagNote;
import org.springframework.data.repository.CrudRepository;

/**
 * Command to link a tag to a note
 */
public class LinkTagToNoteCommand implements Command {

    private final CrudRepository<TagNote, Long> tagNoteRepository;
    private final Long tagId;
    private final String noteId;
    private TagNote savedTagNote;

    /**
     * Create a new LinkTagToNoteCommand
     * 
     * @param tagNoteRepository the repository to save the tag-note link to
     * @param tagId             the ID of the tag to link
     * @param noteId            the ID of the note to link
     */
    public LinkTagToNoteCommand(CrudRepository<TagNote, Long> tagNoteRepository, Long tagId, String noteId) {
        this.tagNoteRepository = tagNoteRepository;
        this.tagId = tagId;
        this.noteId = noteId;
    }

    /**
     * Execute the command by creating and saving a new tag-note link
     * 
     * @return the saved tag-note link
     */
    @Override
    public Object execute() {
        TagNote tagNote = new TagNote(tagId, noteId);
        savedTagNote = tagNoteRepository.save(tagNote);
        return savedTagNote;
    }

    /**
     * Undo the command by deleting the tag-note link
     * 
     * @return true if the link was deleted, false otherwise
     */
    @Override
    public boolean undo() {
        if (savedTagNote != null) {
            tagNoteRepository.delete(savedTagNote);
            return true;
        }
        return false;
    }
}
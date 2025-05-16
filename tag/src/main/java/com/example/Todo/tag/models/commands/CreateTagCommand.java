package com.example.Todo.tag.models.commands;

import com.example.Todo.tag.models.Tag;
import org.springframework.data.repository.CrudRepository;

/**
 * Command to create a new tag
 */
public class CreateTagCommand implements Command {

    private final CrudRepository<Tag, Long> tagRepository;
    private final Tag tag;
    private Tag savedTag;

    /**
     * Create a new CreateTagCommand
     * 
     * @param tagRepository the repository to save the tag to
     * @param tag           the tag to create
     */
    public CreateTagCommand(CrudRepository<Tag, Long> tagRepository, Tag tag) {
        this.tagRepository = tagRepository;
        this.tag = tag;
    }

    /**
     * Execute the command by saving the tag to the repository
     * 
     * @return the saved tag
     */
    @Override
    public Object execute() {
        savedTag = tagRepository.save(tag);
        return savedTag;
    }

    /**
     * Undo the command by deleting the tag if it was saved
     * 
     * @return true if the tag was deleted, false otherwise
     */
    @Override
    public boolean undo() {
        if (savedTag != null) {
            tagRepository.delete(savedTag);
            return true;
        }
        return false;
    }
}
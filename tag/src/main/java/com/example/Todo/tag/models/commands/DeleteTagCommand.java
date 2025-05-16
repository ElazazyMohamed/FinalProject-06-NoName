package com.example.Todo.tag.models.commands;

import com.example.Todo.tag.models.Tag;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

/**
 * Command to delete an existing tag
 */
public class DeleteTagCommand implements Command {

    private final CrudRepository<Tag, Long> tagRepository;
    private final Long tagId;
    private Tag deletedTag;

    /**
     * Create a new DeleteTagCommand
     * 
     * @param tagRepository the repository to delete the tag from
     * @param tagId         the ID of the tag to delete
     */
    public DeleteTagCommand(CrudRepository<Tag, Long> tagRepository, Long tagId) {
        this.tagRepository = tagRepository;
        this.tagId = tagId;
    }

    /**
     * Execute the command by finding and deleting the tag
     * 
     * @return the deleted tag or null if not found
     */
    @Override
    public Object execute() {
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        if (optionalTag.isPresent()) {
            deletedTag = optionalTag.get();
            tagRepository.delete(deletedTag);
            return deletedTag;
        }
        return null;
    }

    /**
     * Undo the command by restoring the deleted tag
     * 
     * @return true if the tag was restored, false otherwise
     */
    @Override
    public boolean undo() {
        if (deletedTag != null) {
            tagRepository.save(deletedTag);
            return true;
        }
        return false;
    }
}
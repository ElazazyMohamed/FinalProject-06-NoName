package com.example.Todo.tag.models.commands;

import com.example.Todo.tag.models.Tag;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

/**
 * Command to update an existing tag
 */
public class UpdateTagCommand implements Command {

    private final CrudRepository<Tag, Long> tagRepository;
    private final Long tagId;
    private final Tag updatedTag;
    private Tag originalTag;

    /**
     * Create a new UpdateTagCommand
     * 
     * @param tagRepository the repository to update the tag in
     * @param tagId         the ID of the tag to update
     * @param updatedTag    the updated tag data
     */
    public UpdateTagCommand(CrudRepository<Tag, Long> tagRepository, Long tagId, Tag updatedTag) {
        this.tagRepository = tagRepository;
        this.tagId = tagId;
        this.updatedTag = updatedTag;
    }

    /**
     * Execute the command by finding and updating the tag
     * 
     * @return the updated tag or null if not found
     */
    @Override
    public Object execute() {
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        if (optionalTag.isPresent()) {
            originalTag = optionalTag.get();

            // Store original values before update for potential undo
            Tag temp = new Tag();
            temp.setId(originalTag.getId());
            temp.setName(originalTag.getName());
            temp.setColor(originalTag.getColor());
            temp.setDescription(originalTag.getDescription());
            temp.setUserId(originalTag.getUserId());
            temp.setCreatedAt(originalTag.getCreatedAt());
            temp.setUpdatedAt(originalTag.getUpdatedAt());
            originalTag = temp;

            // Update with new values
            Tag tagToUpdate = optionalTag.get();
            tagToUpdate.setName(updatedTag.getName());
            tagToUpdate.setColor(updatedTag.getColor());
            tagToUpdate.setDescription(updatedTag.getDescription());

            return tagRepository.save(tagToUpdate);
        }
        return null;
    }

    /**
     * Undo the command by restoring the original tag state
     * 
     * @return true if the tag was restored, false otherwise
     */
    @Override
    public boolean undo() {
        if (originalTag != null) {
            tagRepository.save(originalTag);
            return true;
        }
        return false;
    }
}
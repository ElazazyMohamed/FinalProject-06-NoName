package com.example.Todo.tag.repositories;

import com.example.Todo.tag.models.TagNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for TagNote entities
 */
@Repository
public interface TagNoteRepository extends JpaRepository<TagNote, Long> {

    /**
     * Find all tag-note associations for a specific tag
     * 
     * @param tagId the ID of the tag
     * @return list of tag-note associations for the tag
     */
    List<TagNote> findByTagId(Long tagId);

    /**
     * Find all tag-note associations for a specific note
     * 
     * @param noteId the ID of the note
     * @return list of tag-note associations for the note
     */
    List<TagNote> findByNoteId(String noteId);

    /**
     * Find a tag-note association by tag ID and note ID
     * 
     * @param tagId  the ID of the tag
     * @param noteId the ID of the note
     * @return the tag-note association, or null if not found
     */
    TagNote findByTagIdAndNoteId(Long tagId, String noteId);

    /**
     * Delete a tag-note association by tag ID and note ID
     * 
     * @param tagId  the ID of the tag
     * @param noteId the ID of the note
     */
    void deleteByTagIdAndNoteId(Long tagId, String noteId);

    /**
     * Delete all tag-note associations for a specific tag
     * 
     * @param tagId the ID of the tag
     */
    void deleteByTagId(Long tagId);

    /**
     * Delete all tag-note associations for a specific note
     * 
     * @param noteId the ID of the note
     */
    void deleteByNoteId(String noteId);
}

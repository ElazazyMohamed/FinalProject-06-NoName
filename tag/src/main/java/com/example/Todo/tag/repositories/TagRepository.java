package com.example.Todo.tag.repositories;

import com.example.Todo.tag.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Tag entities
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * Find all tags for a specific user
     * 
     * @param userId the ID of the user
     * @return list of tags belonging to the user
     */
    List<Tag> findByUserId(Long userId);

    /**
     * Find a tag by name
     * 
     * @param name the name of the tag
     * @return the tag with the specified name, or null if not found
     */
    Tag findByName(String name);

    /**
     * Find tags containing the given text in their name
     * 
     * @param nameContains text to search for in tag names
     * @return list of tags with names containing the specified text
     */
    List<Tag> findByNameContainingIgnoreCase(String nameContains);

    /**
     * Find tags by name and user ID
     * 
     * @param name   the name of the tag
     * @param userId the ID of the user
     * @return the tag with the specified name and user ID, or null if not found
     */
    Tag findByNameAndUserId(String name, Long userId);

    /**
     * Find tags containing the given text in their name for a specific user
     * 
     * @param nameContains text to search for in tag names
     * @param userId       the ID of the user
     * @return list of tags with names containing the specified text and belonging
     *         to the user
     */
    List<Tag> findByNameContainingIgnoreCaseAndUserId(String nameContains, Long userId);
}

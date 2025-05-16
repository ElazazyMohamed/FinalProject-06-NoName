package com.example.user.repository;
import com.example.user.models.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BaseUser, Integer> {
    Optional<BaseUser> findUserByEmail(String email);
    @Query("SELECT u FROM BaseUser u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :emailSubstring, '%')) AND u.status = 'ACTIVE'")
    List<BaseUser> findByEmailContainingIgnoreCase(String emailSubstring);
}
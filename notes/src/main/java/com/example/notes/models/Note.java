package com.example.notes.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

// Product class representing a note

@Document(collection = "notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @Id
    private String id;
    private String title;
    private String content;
    private Integer userId;
    private List<String> tags;
    private Instant createdAt;
    private Instant updatedAt;
    private Status status;

   public Note(Builder builder) {
        this.title = builder.getTitle();
        this.content = builder.getContent();
        this.userId = builder.getUserId();
        this.tags = builder.getTags();
        this.createdAt = builder.getCreatedAt();
        this.updatedAt = builder.getUpdatedAt();
        this.status = builder.getStatus();
   }
}

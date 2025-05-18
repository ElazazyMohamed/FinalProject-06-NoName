package com.example.notification.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notification {
    @Id
    private String id;
    private Integer userId;
    private String type; // e.g., "EMAIL", "SMS"
    private String content;
    private LocalDateTime createdAt;
    private boolean isRead;

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}

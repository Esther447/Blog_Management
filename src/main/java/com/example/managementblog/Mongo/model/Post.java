package com.example.managementblog.Mongo.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    private String id;
    private String title;
    private String content;
    private String author;
    private String date;
    private String category;
    private LocalDateTime createdAt;
    private List<Comment> comments;
}

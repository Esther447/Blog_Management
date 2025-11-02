package com.example.managementblog.Mongo.DTO;

import com.example.managementblog.Mongo.model.Comment;
import lombok.Data;

import java.util.List;

@Data
public class PostDTO {
    private String title;
    private String content;
    private String author;
    private List<CommentDTO> comments;
}

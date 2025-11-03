package com.example.managementblog.Mongo.controller;

import com.example.managementblog.Mongo.model.Comment;
import com.example.managementblog.Mongo.repository.CommentRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // Create a comment
    @PostMapping
    public ResponseEntity<Comment> createComment(@Valid @RequestBody Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        return ResponseEntity.ok(savedComment);
    }

    // Get all comments
    @GetMapping
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // Get comment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable String id) {
        return commentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update a comment
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable String id, @Valid @RequestBody Comment updatedComment) {
        return commentRepository.findById(id).map(comment -> {
            comment.setContent(updatedComment.getContent());
            comment.setPostId(updatedComment.getPostId());
            comment.setUserId(updatedComment.getUserId());
            commentRepository.save(comment);
            return ResponseEntity.ok(comment);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete a comment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable String id) {
        return commentRepository.findById(id).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // Search comments with optional filters and pagination
    @GetMapping("/search")
    public Page<Comment> searchComments(
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        if (content != null && postId != null) {
            return commentRepository.findByContentContainingIgnoreCaseAndPostId(content, postId, pageable);
        } else if (content != null) {
            return commentRepository.findByContentContainingIgnoreCase(content, pageable);
        } else if (postId != null) {
            return commentRepository.findByPostId(postId, pageable);
        } else {
            return commentRepository.findAll(pageable);
        }
    }
}

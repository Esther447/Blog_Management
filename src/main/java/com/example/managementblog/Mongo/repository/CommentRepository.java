package com.example.managementblog.Mongo.repository;

import com.example.managementblog.Mongo.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {

    // Search comments by content (case-insensitive) with pagination
    Page<Comment> findByContentContainingIgnoreCase(String content, Pageable pageable);

    // Get comments for a specific post with pagination
    Page<Comment> findByPostId(String postId, Pageable pageable);

    // Search by content or filter by postId, paginated
    Page<Comment> findByContentContainingIgnoreCaseOrPostId(String content, String postId, Pageable pageable);

    Page<Comment> findByContentContainingIgnoreCaseAndPostId(String content, String postId, Pageable pageable);
}

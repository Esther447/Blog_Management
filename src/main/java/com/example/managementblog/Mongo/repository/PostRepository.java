package com.example.managementblog.Mongo.repository;

import com.example.managementblog.Mongo.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
    Page<Post> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Post> findByContentContainingIgnoreCase(String content, Pageable pageable);
    Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content, Pageable pageable);
}

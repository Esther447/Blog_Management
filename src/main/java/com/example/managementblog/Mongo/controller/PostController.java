package com.example.managementblog.Mongo.controller;

import com.example.managementblog.Mongo.model.Post;
import com.example.managementblog.Mongo.repository.PostRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts API", description = "Handles Blog Post CRUD operations")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Operation(summary = "Create a new post")
    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
        log.info("Creating new post...");
        log.debug("Post payload received: {}", post);

        Post savedPost = postRepository.save(post);
        return ResponseEntity.ok(savedPost);
    }

    @Operation(summary = "Get all posts")
    @GetMapping
    public List<Post> getAllPosts() {
        log.info("Fetching all posts...");
        return postRepository.findAll();
    }

    @Operation(summary = "Get post by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable String id) {
        log.info("Fetching post by ID: {}", id);

        return postRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update post")
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable String id,
            @Valid @RequestBody Post updatedPost) {

        log.info("Updating post with ID: {}", id);
        log.debug("Updated post payload: {}", updatedPost);

        return postRepository.findById(id).map(post -> {
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            post.setUserId(updatedPost.getUserId());
            post.setCommentIds(updatedPost.getCommentIds());
            postRepository.save(post);
            return ResponseEntity.ok(post);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete post")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        log.warn("Deleting post by ID: {}", id);

        return postRepository.findById(id).map(post -> {
            postRepository.delete(post);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Search post with pagination")
    @GetMapping("/search")
    public Page<Post> searchPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Searching posts | title: {} | content: {}", title, content);

        Pageable pageable = PageRequest.of(page, size);

        if (title != null && content != null) {
            return postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(title, content, pageable);
        } else if (title != null) {
            return postRepository.findByTitleContainingIgnoreCase(title, pageable);
        } else if (content != null) {
            return postRepository.findByContentContainingIgnoreCase(content, pageable);
        } else {
            return postRepository.findAll(pageable);
        }
    }
}

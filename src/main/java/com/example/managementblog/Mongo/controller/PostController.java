package com.example.managementblog.Mongo.controller;

import com.example.managementblog.Mongo.model.Post;
import com.example.managementblog.Mongo.repository.PostRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Create a new post
    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
        Post savedPost = postRepository.save(post);
        return ResponseEntity.ok(savedPost);
    }

    // Get all posts
    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Get post by ID
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable String id) {
        return postRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update a post
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @Valid @RequestBody Post updatedPost) {
        return postRepository.findById(id).map(post -> {
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            post.setUserId(updatedPost.getUserId());
            post.setCommentIds(updatedPost.getCommentIds());
            postRepository.save(post);
            return ResponseEntity.ok(post);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete a post
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        return postRepository.findById(id).map(post -> {
            postRepository.delete(post);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // Search posts with pagination
    @GetMapping("/search")
    public Page<Post> searchPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
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

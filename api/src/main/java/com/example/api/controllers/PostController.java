package com.example.api.controllers;

import com.example.api.models.responses.PostsResponse;
import com.example.api.models.schemas.Comment;
import com.example.api.models.schemas.Post;
import com.example.api.services.implementations.PostsImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostsImplementation postsImplementation;

    @GetMapping("/get/{id}")
    public ResponseEntity<PostsResponse> getPost(@PathVariable("id") UUID post_id) throws Exception {
        return ResponseEntity.ok(
                PostsResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Post retrieved")
                        .data(postsImplementation.get(post_id))
                        .build()
        );
    }

    @GetMapping("/list")
    public ResponseEntity<PostsResponse> getPosts() throws Exception {
        return ResponseEntity.ok(
                PostsResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Posts retrieved")
                        .data(postsImplementation.list())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<PostsResponse> savePost(@RequestBody Post post) throws Exception {
        return ResponseEntity.ok(
                PostsResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Post saved")
                        .data(postsImplementation.create(post))
                        .build()
        );
    }

    @PostMapping("/comment/save/{id}")
    public ResponseEntity<PostsResponse> saveComment(@PathVariable("id") UUID post_id,@RequestBody Comment comment) throws Exception {
        return ResponseEntity.ok(
                PostsResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Comment saved")
                        .data(postsImplementation.createComment(post_id,comment))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PostsResponse> updatePost(@PathVariable("id") UUID post_id, @RequestBody Post post) throws Exception {
        return ResponseEntity.ok(
                PostsResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Post updated")
                        .data(postsImplementation.update(post_id,post))
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PostsResponse> deletePost(@PathVariable("id") UUID post_id) throws Exception {
        return ResponseEntity.ok(
                PostsResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Post deleted")
                        .data(postsImplementation.delete(post_id))
                        .build()
        );
    }

    @DeleteMapping("/comment/delete/{id}/{comment_id}")
    public ResponseEntity<PostsResponse> deleteComment(@PathVariable("id") UUID post_id,@PathVariable("comment_id") UUID comment_id) throws Exception {
        return ResponseEntity.ok(
                PostsResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Comment deleted")
                        .data(postsImplementation.deleteComment(post_id,comment_id))
                        .build()
        );
    }
}

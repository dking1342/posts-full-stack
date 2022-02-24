package com.example.api.controllers;

import com.example.api.models.responses.PostLikeResponse;
import com.example.api.models.responses.PostsResponse;
import com.example.api.models.schemas.PostLike;
import com.example.api.services.implementations.PostLikeImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/postlikes")
public class PostLikeController {
    private final PostLikeImplementation postLikeImplementation;

    @GetMapping("/get/{id}")
    public ResponseEntity<PostLikeResponse> getVote(@PathVariable("id")UUID vote_id) throws Exception {
        return ResponseEntity.ok(
                PostLikeResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Vote retrieved")
                        .data(postLikeImplementation.get(vote_id))
                        .build()
        );
    }

    @GetMapping("/list")
    public ResponseEntity<PostLikeResponse> getVotes() throws Exception {
        return ResponseEntity.ok(
                PostLikeResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Votes retrieved")
                        .data(postLikeImplementation.list())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<PostsResponse> saveVote(@RequestBody PostLike postLike) throws Exception {
        return ResponseEntity.ok(
                PostsResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Vote saved")
                        .data(postLikeImplementation.create(postLike))
                        .build()
        );
    }

    @PostMapping("/comment/save/{id}")
    public ResponseEntity<PostsResponse> saveCommentVote(@PathVariable("id") UUID comment_id, @RequestBody PostLike postLike) throws Exception {
        return ResponseEntity.ok(
                PostsResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Comment vote saved")
                        .data(postLikeImplementation.createComment(postLike,comment_id))
                        .build()
        );
    }



}

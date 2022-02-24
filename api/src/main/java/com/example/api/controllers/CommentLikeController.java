package com.example.api.controllers;

import com.example.api.models.responses.CommentLikeResponse;
import com.example.api.models.responses.PostsResponse;
import com.example.api.models.schemas.CommentLike;
import com.example.api.services.implementations.CommentLikeImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/commentlikes")
public class CommentLikeController {
    private final CommentLikeImplementation commentLikeImplementation;

    @GetMapping("/list")
    public ResponseEntity<CommentLikeResponse> getVotes() throws Exception {
        return ResponseEntity.ok(
                CommentLikeResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Votes retrieved")
                        .data(commentLikeImplementation.list())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<PostsResponse> saveVote(@RequestBody CommentLike commentLike) throws Exception {
        return ResponseEntity.ok(
                PostsResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Vote saved")
                        .data(commentLikeImplementation.create(commentLike))
                        .build()
        );
    }
}

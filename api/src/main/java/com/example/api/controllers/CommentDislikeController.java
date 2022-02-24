package com.example.api.controllers;

import com.example.api.models.responses.CommentDislikeResponse;
import com.example.api.models.responses.PostsResponse;
import com.example.api.models.schemas.CommentDislike;
import com.example.api.services.implementations.CommentDislikeImplementation;
import com.example.api.services.implementations.CommentLikeImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/commentdislikes")
public class CommentDislikeController {
    private final CommentDislikeImplementation commentDislikeImplementation;

    @GetMapping("/list")
    public ResponseEntity<CommentDislikeResponse> getVotes() throws Exception {
        return ResponseEntity.ok(
                CommentDislikeResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Votes retrieved")
                        .data(commentDislikeImplementation.list())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<PostsResponse> saveVote(@RequestBody CommentDislike commentDislike) throws Exception {
        return ResponseEntity.ok(
                PostsResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Vote saved")
                        .data(commentDislikeImplementation.create(commentDislike))
                        .build()
        );
    }
}

package com.example.api.controllers;

import com.example.api.models.responses.PostDislikeResponse;
import com.example.api.models.responses.PostsResponse;
import com.example.api.models.schemas.PostDislike;
import com.example.api.services.implementations.PostDislikeImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/postdislikes")
public class PostDislikeController {
    private final PostDislikeImplementation postDislikeImplementation;

    @GetMapping("/get/{id}")
    public ResponseEntity<PostDislikeResponse> getVote(@PathVariable("id")UUID vote_id) throws Exception {
        return ResponseEntity.ok(
          PostDislikeResponse.builder()
                  .timestamp(LocalDateTime.now())
                  .status(HttpStatus.OK)
                  .statusCode(HttpStatus.OK.value())
                  .message("Vote retrieved")
                  .data(postDislikeImplementation.get(vote_id))
                  .build()
        );
    }

    @GetMapping("/list")
    public ResponseEntity<PostDislikeResponse> getVotes() throws Exception {
        return ResponseEntity.ok(
                PostDislikeResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Votes retrieved")
                        .data(postDislikeImplementation.list())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<PostsResponse> saveVote(@RequestBody PostDislike postDislike) throws Exception {
        return ResponseEntity.ok(
                PostsResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Vote saved")
                        .data(postDislikeImplementation.create(postDislike))
                        .build()
        );
    }
}

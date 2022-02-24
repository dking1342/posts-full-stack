package com.example.api.controllers;

import com.example.api.models.responses.UserResponse;
import com.example.api.models.schemas.User;
import com.example.api.services.implementations.UserImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserImplementation userImplementation;

    @GetMapping("/get/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("username") String username) throws Exception {
        return ResponseEntity.ok(
                UserResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("User retrieved")
                        .data(userImplementation.get(username))
                        .build()
        );
    }

    @GetMapping("/list")
    public ResponseEntity<UserResponse> getUsers() throws Exception {
        return ResponseEntity.ok(
                UserResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Users retrieved")
                        .data(userImplementation.list())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<UserResponse> saveUser(@RequestBody User user) throws Exception {
        return ResponseEntity.ok(
                UserResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("User saved")
                        .data(userImplementation.create(user))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id")UUID user_id, @RequestBody User user) throws Exception {
        return ResponseEntity.ok(
                UserResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("User updated")
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable("id")UUID user_id) throws Exception {
        return ResponseEntity.ok(
                UserResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("User delete")
                        .build()
        );
    }
}

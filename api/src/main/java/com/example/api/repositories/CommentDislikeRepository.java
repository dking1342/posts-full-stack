package com.example.api.repositories;

import com.example.api.models.schemas.CommentDislike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentDislikeRepository extends JpaRepository<CommentDislike, UUID> {
}

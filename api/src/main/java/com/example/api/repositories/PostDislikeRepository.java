package com.example.api.repositories;

import com.example.api.models.schemas.PostDislike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostDislikeRepository extends JpaRepository<PostDislike, UUID> {
}

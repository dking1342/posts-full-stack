package com.example.api.models.responses;

import com.example.api.models.schemas.PostLike;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostLikeResponse {
    protected LocalDateTime timestamp;
    protected HttpStatus status;
    protected int statusCode;
    protected String message;
    protected ArrayList<PostLike> data;
}

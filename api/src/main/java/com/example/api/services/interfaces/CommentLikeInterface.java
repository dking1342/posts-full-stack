package com.example.api.services.interfaces;

import com.example.api.models.schemas.CommentLike;
import com.example.api.models.schemas.Post;

import java.util.ArrayList;

public interface CommentLikeInterface {
    ArrayList<CommentLike> list() throws Exception;
    ArrayList<Post> create(CommentLike commentLike) throws Exception;
}

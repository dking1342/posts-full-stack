package com.example.api.services.interfaces;

import com.example.api.models.schemas.Post;
import com.example.api.models.schemas.PostLike;

import java.util.ArrayList;
import java.util.UUID;

public interface PostLikeInterface {
    ArrayList<PostLike> get(UUID vote_id) throws Exception;
    ArrayList<PostLike> list() throws Exception;
    ArrayList<Post> create(PostLike postLike) throws Exception;
    ArrayList<Post> createComment(PostLike postLike, UUID comment_id) throws Exception;
}

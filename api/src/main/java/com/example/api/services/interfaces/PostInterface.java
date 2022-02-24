package com.example.api.services.interfaces;

import com.example.api.models.schemas.Comment;
import com.example.api.models.schemas.Post;

import java.util.ArrayList;
import java.util.UUID;

public interface PostInterface {
    ArrayList<Post> get(UUID post_id) throws Exception;
    ArrayList<Post> list() throws Exception;
    ArrayList<Post> create(Post post) throws Exception;
    ArrayList<Post> update(UUID post_id, Post post) throws Exception;
    ArrayList<Post> delete(UUID post_id) throws Exception;
    ArrayList<Post> createComment(UUID post_id, Comment comment) throws Exception;
    ArrayList<Post> deleteComment(UUID post_id, UUID comment_id) throws Exception;
}

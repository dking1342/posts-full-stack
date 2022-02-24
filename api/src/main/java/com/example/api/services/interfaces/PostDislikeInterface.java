package com.example.api.services.interfaces;

import com.example.api.models.schemas.Post;
import com.example.api.models.schemas.PostDislike;

import java.util.ArrayList;
import java.util.UUID;

public interface PostDislikeInterface {
    ArrayList<PostDislike> get(UUID vote_id) throws Exception;
    ArrayList<PostDislike> list() throws Exception;
    ArrayList<Post> create(PostDislike postDislike) throws Exception;
}

package com.example.api.services.interfaces;

import com.example.api.models.schemas.CommentDislike;
import com.example.api.models.schemas.Post;

import java.util.ArrayList;

public interface CommentDislikeInterface {
    ArrayList<CommentDislike> list() throws Exception;
    ArrayList<Post> create(CommentDislike commentDislike) throws Exception;
}

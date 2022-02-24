package com.example.api.services.implementations;

import com.example.api.models.schemas.Comment;
import com.example.api.models.schemas.Post;
import com.example.api.models.schemas.User;
import com.example.api.repositories.PostRepository;
import com.example.api.repositories.UserRepository;
import com.example.api.services.interfaces.PostInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostsImplementation implements PostInterface {
    private final PostRepository postRepository;

    @Override
    public ArrayList<Post> get (UUID post_id) throws Exception {
        log.info("Fetching post id: {}",post_id);
        Optional<Post> fetchedPost = postRepository.findById((post_id));
        if(fetchedPost.isPresent()){
            ArrayList<Post> responsePost = new ArrayList<>();
            responsePost.add(fetchedPost.get());
            return responsePost;
        } else {
            throw new Exception("Post does not exists");
        }
    }

    @Override
    public ArrayList<Post> list() throws Exception {
        log.info("Fetching all posts");
        ArrayList<Post> fetchedPosts = (ArrayList<Post>) postRepository.findAll();
        if(fetchedPosts.isEmpty()){
            throw new Exception("No Posts");
        } else {
            return fetchedPosts;
        }
    }

    @Override
    public ArrayList<Post> create(Post post) throws Exception {
        log.info("Saving post");
        Post newPost = new Post(
            UUID.randomUUID(),
            post.getPost_title(),
            post.getPost_body(),
            post.getPost_author(),
            0,
            0,
            new ArrayList<>(),
            post.getPost_createdAt(),
            new ArrayList<>()
        );

        Post savedPost = postRepository.save(newPost);
        ArrayList<Post> responsePost = new ArrayList<>();
        responsePost.add(savedPost);
        return responsePost;
    }

    @Override
    public ArrayList<Post> update(UUID post_id, Post post) throws Exception {
        log.info("Updated post with id: {}",post_id);
        Optional<Post> fetchedPost = postRepository.findById(post_id);
        if(fetchedPost.isPresent()){
            Post updatePost = new Post(
                    post_id,
                    post.getPost_title(),
                    post.getPost_body(),
                    post.getPost_author(),
                    post.getPost_likes(),
                    post.getPost_dislikes(),
                    post.getComments(),
                    post.getPost_createdAt(),
                    post.getPost_votes()
            );
            Post savedPost = postRepository.save(updatePost);
            System.out.println(savedPost);
            ArrayList<Post> responsePost = new ArrayList<>();
            responsePost.add(savedPost);
            return responsePost;
        } else {
            throw new Exception("Post does not exist");
        }
    }

    @Override
    public ArrayList<Post> delete(UUID post_id) throws Exception {
        log.info("Deleting post with id: {}",post_id);
        Optional<Post> fetchedPost = postRepository.findById(post_id);
        if(fetchedPost.isPresent()){
            postRepository.deleteById(post_id);
            return new ArrayList<>();
        } else {
            throw new Exception("Post does not exist");
        }
    }

    @Override
    public ArrayList<Post> createComment(UUID post_id, Comment comment) throws Exception {
        log.info("Adding comment to post");
        Optional<Post> fetchedPost = postRepository.findById(post_id);
        if(fetchedPost.isPresent()){
            List<Comment> commentList = fetchedPost.get().getComments();
            comment.setComment_createdAt(LocalDateTime.now());
            commentList.add(comment);
            fetchedPost.get().setComments(commentList);

            Post savedPost = postRepository.save(fetchedPost.get());
            ArrayList<Post> responsePost = new ArrayList<>();
            responsePost.add(savedPost);
            return responsePost;
        } else {
            throw new Exception("Post does not exist");
        }
    }

    @Override
    public ArrayList<Post> deleteComment(UUID post_id, UUID comment_id) throws Exception {
        log.info("Deleting comment");
        Optional<Post> fetchedPost = postRepository.findById(post_id);
        if(fetchedPost.isPresent()){
            List<Comment> comment = fetchedPost.get().getComments().stream().filter(item -> item.getComment_id().equals(comment_id)).collect(Collectors.toList());
            if(comment.isEmpty()){
                throw new Exception("Comment does not exist");
            } else {
                Comment commentObj = comment.get(0);
                fetchedPost.get().getComments().remove(commentObj);
                postRepository.save(fetchedPost.get());
                return new ArrayList<>();
            }
        } else {
            throw new Exception("Post does not exist");
        }
    }
}

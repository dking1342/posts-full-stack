package com.example.api.services.implementations;

import com.example.api.models.schemas.Comment;
import com.example.api.models.schemas.CommentLike;
import com.example.api.models.schemas.Post;
import com.example.api.repositories.CommentLikeRepository;
import com.example.api.repositories.CommentRepository;
import com.example.api.repositories.PostRepository;
import com.example.api.services.interfaces.CommentLikeInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class CommentLikeImplementation implements CommentLikeInterface {
    private final CommentLikeRepository commentLikeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    @Override
    public ArrayList<CommentLike> list() throws Exception {
        log.info("Fetching all comment votes");
        return (ArrayList<CommentLike>) commentLikeRepository.findAll();
    }

    @Override
    public ArrayList<Post> create(CommentLike commentLike) throws Exception {
        List<CommentLike> userMatches = commentLikeRepository.findAll().stream().filter(item-> item.getUser_id().equals(commentLike.getUser_id())).collect(Collectors.toList());
        List<CommentLike> commentAndUserMatch = userMatches.stream().filter(item-> item.getComment_id().equals(commentLike.getComment_id())).collect(Collectors.toList());

        if(commentAndUserMatch.isEmpty()){
            log.info("Saving comment vote");
            // setting vote id
            commentLike.setVote_id(UUID.randomUUID());

            // setting comment likes
            int likes = commentLike.getValue() + commentLike.getComment().getComment_likes();
            commentLike.getComment().setComment_likes(likes);

            // setting the post
            Optional<Post> fetchedPost = postRepository.findById(commentLike.getPost_id());
            if(fetchedPost.isPresent()){
                for (Comment comment : fetchedPost.get().getComments()) {
                    if(comment.getComment_id().equals(commentLike.getComment_id())){
                        comment.setComment_likes(likes);
                    }
                }
                postRepository.save(fetchedPost.get());
                commentRepository.save(commentLike.getComment());
                commentLikeRepository.save(commentLike);
                List<Post> postList = postRepository.findAll();
                return (ArrayList<Post>) postList;
            } else {
                throw new Exception("Post does not exist");
            }
        } else {
            log.info("Updating comment vote with id: {}", commentAndUserMatch.get(0).getVote_id());
            // set likes
            int newCommentValue = commentAndUserMatch.get(0).getValue() * -1;
            int newCommentLikes = commentAndUserMatch.get(0).getComment().getComment_likes() + newCommentValue;

            // reset the vote object
            commentAndUserMatch.get(0).setValue(newCommentValue);
            commentAndUserMatch.get(0).getComment().setComment_likes(newCommentLikes);

            Optional<Post> fetchedPost = postRepository.findById(commentLike.getPost_id());
            if(fetchedPost.isPresent()){
                for (Comment comment : fetchedPost.get().getComments()) {
                    if(comment.getComment_id().equals(commentLike.getComment_id())){
                        comment.setComment_likes(newCommentLikes);
                    }
                }
                postRepository.save(fetchedPost.get());
                commentRepository.save(commentAndUserMatch.get(0).getComment());
                commentLikeRepository.save(commentAndUserMatch.get(0));
                List<Post> postList = postRepository.findAll();
                return (ArrayList<Post>) postList;
            } else {
                throw new Exception("Post does not exist");
            }
        }
    }
}

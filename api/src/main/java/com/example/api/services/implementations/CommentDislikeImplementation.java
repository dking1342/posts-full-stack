package com.example.api.services.implementations;

import com.example.api.models.schemas.Comment;
import com.example.api.models.schemas.CommentDislike;
import com.example.api.models.schemas.Post;
import com.example.api.repositories.CommentDislikeRepository;
import com.example.api.repositories.CommentRepository;
import com.example.api.repositories.PostRepository;
import com.example.api.services.interfaces.CommentDislikeInterface;
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
public class CommentDislikeImplementation implements CommentDislikeInterface {
    private final CommentDislikeRepository commentDislikeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    @Override
    public ArrayList<CommentDislike> list() throws Exception {
        log.info("Fetching all comment votes");
        return (ArrayList<CommentDislike>) commentDislikeRepository.findAll();
    }

    @Override
    public ArrayList<Post> create(CommentDislike commentDislike) throws Exception {
        List<CommentDislike> userMatches = commentDislikeRepository.findAll().stream().filter(item-> item.getUser_id().equals(commentDislike.getUser_id())).collect(Collectors.toList());
        List<CommentDislike> commentAndUserMatch = userMatches.stream().filter(item-> item.getComment_id().equals(commentDislike.getComment_id())).collect(Collectors.toList());

        if(commentAndUserMatch.isEmpty()){
            log.info("Saving comment vote");
            // setting vote id
            commentDislike.setVote_id(UUID.randomUUID());

            // setting comment likes
            int dislikes = commentDislike.getValue() + commentDislike.getComment().getComment_dislikes();
            commentDislike.getComment().setComment_dislikes(dislikes);

            // setting the post
            Optional<Post> fetchedPost = postRepository.findById(commentDislike.getPost_id());
            if(fetchedPost.isPresent()){
                for (Comment comment : fetchedPost.get().getComments()) {
                    if(comment.getComment_id().equals(commentDislike.getComment_id())){
                        comment.setComment_dislikes(dislikes);
                    }
                }
                postRepository.save(fetchedPost.get());
                commentRepository.save(commentDislike.getComment());
                commentDislikeRepository.save(commentDislike);
                List<Post> postList = postRepository.findAll();
                return (ArrayList<Post>) postList;
            } else {
                throw new Exception("Post does not exist");
            }
        } else {
            log.info("Updating comment vote with id: {}", commentAndUserMatch.get(0).getVote_id());
            // set likes
            int newCommentValue = commentAndUserMatch.get(0).getValue() * -1;
            int newCommentDislikes = commentAndUserMatch.get(0).getComment().getComment_dislikes() + newCommentValue;

            // reset the vote object
            commentAndUserMatch.get(0).setValue(newCommentValue);
            commentAndUserMatch.get(0).getComment().setComment_dislikes(newCommentDislikes);

            Optional<Post> fetchedPost = postRepository.findById(commentDislike.getPost_id());
            if(fetchedPost.isPresent()){
                for (Comment comment : fetchedPost.get().getComments()) {
                    if(comment.getComment_id().equals(commentDislike.getComment_id())){
                        comment.setComment_dislikes(newCommentDislikes);
                    }
                }
                postRepository.save(fetchedPost.get());
                commentRepository.save(commentAndUserMatch.get(0).getComment());
                commentDislikeRepository.save(commentAndUserMatch.get(0));
                List<Post> postList = postRepository.findAll();
                return (ArrayList<Post>) postList;
            } else {
                throw new Exception("Post does not exist");
            }
        }
    }
}

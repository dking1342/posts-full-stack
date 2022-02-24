package com.example.api.services.implementations;

import com.example.api.models.schemas.Comment;
import com.example.api.models.schemas.Post;
import com.example.api.models.schemas.PostLike;
import com.example.api.repositories.CommentRepository;
import com.example.api.repositories.PostLikeRepository;
import com.example.api.repositories.PostRepository;
import com.example.api.services.interfaces.PostLikeInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostLikeImplementation implements PostLikeInterface {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public ArrayList<PostLike> get(UUID vote_id) throws Exception {
        log.info("Fetching vote id: {}",vote_id);
        Optional<PostLike> fetchedVote = postLikeRepository.findById(vote_id);
        if(fetchedVote.isPresent()){
            ArrayList<PostLike> responseVote = new ArrayList<>();
            responseVote.add(fetchedVote.get());
            return responseVote;
        } else {
            throw new Exception("Vote does not exist");
        }
    }

    @Override
    public ArrayList<PostLike> list() throws Exception {
        log.info("Fetching all votes");
        return  (ArrayList<PostLike>) postLikeRepository.findAll();
    }

    @Override
    public ArrayList<Post> create(PostLike postLike) throws Exception {
        List<PostLike> userMatches = postLikeRepository.findAll().stream().filter(item-> item.getUser_id().equals(postLike.getUser_id())).collect(Collectors.toList());
        List<PostLike> postAndUserMatch = userMatches.stream().filter(item-> item.getPost_id().equals(postLike.getPost_id())).collect(Collectors.toList());

        if(postAndUserMatch.isEmpty()){
            log.info("Saving post vote");
            // setting vote id
            postLike.setVote_id(UUID.randomUUID());

            // setting post likes
            int likes = postLike.getValue() + postLike.getPost().getPost_likes();
            postLike.getPost().setPost_likes(likes);

            postRepository.save(postLike.getPost());
            postLikeRepository.save(postLike);
            List<Post> postList = postRepository.findAll();
            return (ArrayList<Post>) postList;
        } else {
            log.info("Updating post vote with id: {}", postAndUserMatch.get(0).getVote_id());
            // setting likes
            int newPostValue = postAndUserMatch.get(0).getValue() * -1;
            int newPostLikes = postAndUserMatch.get(0).getPost().getPost_likes() + newPostValue;

            // reset the vote object
            postAndUserMatch.get(0).setValue(newPostValue);
            postAndUserMatch.get(0).getPost().setPost_likes(newPostLikes);

            postRepository.save(postAndUserMatch.get(0).getPost());
            postLikeRepository.save(postAndUserMatch.get(0));
            List<Post> postList = postRepository.findAll();
            return (ArrayList<Post>) postList;
        }
    }

    @Override
    public ArrayList<Post> createComment(PostLike postLike, UUID comment_id) throws Exception {
        // filter the post
        List<PostLike> postMatches = postLikeRepository.findAll().stream().filter(item-> item.getPost_id().equals(postLike.getPost_id())).collect(Collectors.toList());

        if(postMatches.isEmpty()){
            log.info("Saving comment vote");
            // setting vote id
            postLike.setVote_id(UUID.randomUUID());

            // get comment likes
            List<Comment> commentList = postLike.getPost().getComments().stream().filter(item-> item.getComment_id().equals(comment_id)).collect(Collectors.toList());
            if(commentList.isEmpty()){
                throw new Exception("Comment does not exist");
            } else {
                // set likes for comment
                Comment filteredComment = commentList.get(0);
                int likes = postLike.getValue() + filteredComment.getComment_likes();
                filteredComment.setComment_likes(likes);

                // set post with new likes
                postLike.getPost().getComments().stream().map(item->{
                   if(item.getComment_id().equals(comment_id)){
                       return filteredComment;
                   } else {
                        return item;
                   }
                });

                postRepository.save(postLike.getPost());
                postLikeRepository.save(postLike);
                List<Post> postList = postRepository.findAll();
                return (ArrayList<Post>) postList;
            }
        } else {
            log.info("Saving comment vote");
            // filter for user and comment
            List<PostLike> filteredVotes = postMatches;
            List<PostLike> userMatches = filteredVotes.stream().filter(item-> item.getUser_id().equals(postLike.getUser_id())).collect(Collectors.toList());
            boolean commentMatch = userMatches.iterator().next().getPost().getComments().iterator().next().getComment_id().equals(comment_id);
//            List<Comment> filteredComments = filteredVote.getPost().getComments();
//            List<Comment> commentMatch = filteredComments.stream().filter(item-> item.getcomm)

            System.out.println(userMatches);



            // check if first time vote
            // check if multiple time vote

            // get comment likes
//            List<Comment> commentList = filteredPost.getPost().getComments().stream().filter(item-> item.getComment_id().equals(comment_id)).collect(Collectors.toList());
//            if(commentList.isEmpty()){
//                throw new Exception("Comment does not exist");
//            } else {
//                // set likes for comment
//                Comment filteredComment = commentList.get(0);
//                int likes = postLike.getValue() + filteredComment.getComment_likes();
//                filteredComment.setComment_likes(likes);
//
//                // set post with new likes
//                postLike.getPost().getComments().stream().map(item->{
//                    if(item.getComment_id().equals(comment_id)){
//                        return filteredComment;
//                    } else {
//                        return item;
//                    }
//                });
//
//                postRepository.save(postLike.getPost());
//                postLikeRepository.save(postLike);
//                List<Post> postList = postRepository.findAll();
//                return (ArrayList<Post>) postList;
//            }
//                return null;



        }

        return null;


    }

}

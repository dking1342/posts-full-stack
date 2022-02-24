package com.example.api.services.implementations;

import com.example.api.models.schemas.Post;
import com.example.api.models.schemas.PostDislike;
import com.example.api.repositories.PostDislikeRepository;
import com.example.api.repositories.PostRepository;
import com.example.api.services.interfaces.PostDislikeInterface;
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
public class PostDislikeImplementation implements PostDislikeInterface {
    private final PostDislikeRepository postDislikeRepository;
    private final PostRepository postRepository;


    @Override
    public ArrayList<PostDislike> get(UUID vote_id) throws Exception {
        log.info("Fetching vote id: {}",vote_id);
        Optional<PostDislike> fetchedVote = postDislikeRepository.findById(vote_id);
        if(fetchedVote.isPresent()){
            ArrayList<PostDislike> responseVote = new ArrayList<>();
            responseVote.add(fetchedVote.get());
            return responseVote;
        } else {
            throw new Exception("Vote does not exist");
        }
    }

    @Override
    public ArrayList<PostDislike> list() throws Exception {
        log.info("Fetching all votes");
        ArrayList<PostDislike> fetchedVotes = (ArrayList<PostDislike>) postDislikeRepository.findAll();
        return fetchedVotes;
    }

    @Override
    public ArrayList<Post> create(PostDislike postDislike) throws Exception {
        List<PostDislike> userMatches = postDislikeRepository.findAll().stream().filter(item-> item.getUser_id().equals(postDislike.getUser_id())).collect(Collectors.toList());
        List<PostDislike> postAndUserMatch = userMatches.stream().filter(item-> item.getPost_id().equals(postDislike.getPost_id())).collect(Collectors.toList());

        if(postAndUserMatch.isEmpty()){
            log.info("Saving vote");
            // setting vote id
            postDislike.setVote_id(UUID.randomUUID());

            // setting post dislikes
            int dislikes = postDislike.getValue() + postDislike.getPost().getPost_dislikes();
            postDislike.getPost().setPost_dislikes(dislikes);

            postRepository.save(postDislike.getPost());
            postDislikeRepository.save(postDislike);
            List<Post> postList = postRepository.findAll();
            return (ArrayList<Post>) postList;
        } else {
            log.info("Updating vote with id: {}", postAndUserMatch.get(0).getVote_id());
            // setting dislikes
            int newPostValue = postAndUserMatch.get(0).getValue() * -1;
            int newPostDislikes = postAndUserMatch.get(0).getPost().getPost_dislikes() + newPostValue;

            // reset the vote object
            postAndUserMatch.get(0).setValue(newPostValue);
            postAndUserMatch.get(0).getPost().setPost_dislikes(newPostDislikes);

            postRepository.save(postAndUserMatch.get(0).getPost());
            postDislikeRepository.save(postAndUserMatch.get(0));
            List<Post> postList = postRepository.findAll();
            return (ArrayList<Post>) postList;
        }
    }
}

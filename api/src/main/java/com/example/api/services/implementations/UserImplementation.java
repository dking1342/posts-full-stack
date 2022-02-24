package com.example.api.services.implementations;

import com.example.api.models.schemas.User;
import com.example.api.repositories.UserRepository;
import com.example.api.services.interfaces.UserInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserImplementation implements UserInterface {
    private final UserRepository userRepository;

    @Override
    public ArrayList<User> get(String username) throws Exception {
        log.info("Fetching user: {}",username);
        Optional<User> fetchedUser = Optional.ofNullable(userRepository.findByUsername(username));
        if(fetchedUser.isPresent()){
            ArrayList<User> responseUser = new ArrayList<>();
            responseUser.add(fetchedUser.get());
            return responseUser;
        } else {
            throw new Exception("User does not exist");
        }
    }

    @Override
    public ArrayList<User> list() throws Exception {
        log.info("Fetching all users");
        ArrayList<User> fetchedUsers = (ArrayList<User>) userRepository.findAll();
        if(fetchedUsers.isEmpty()){
            throw new Exception("No users found");
        } else {
            return fetchedUsers;
        }
    }

    @Override
    public ArrayList<User> create(User user) throws Exception {
        log.info("Saving user");
        Optional<User> fetchedUser = Optional.ofNullable(userRepository.findByUsername(user.getUsername()));
        if(fetchedUser.isPresent()){
            throw new Exception("User already exists");
        } else {
            User newUser = new User(
                    UUID.randomUUID(),
                    user.getUsername(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>()
            );
            User savedUser = userRepository.save(newUser);
            ArrayList<User> responseUser = new ArrayList<>();
            responseUser.add(savedUser);
            return responseUser;
        }
    }

    @Override
    public ArrayList<User> update(UUID user_id, User user) throws Exception {
        log.info("Updating user: {}", user_id);
        Optional<User> fetchedUser = userRepository.findById(user_id);
        if(fetchedUser.isPresent()){
            User updatedUser = new User(
                    user.getUser_id(),
                    user.getUsername(),
                    user.getUser_posts(),
                    user.getUser_votes(),
                    user.getUser_comment_votes()
            );
            User savedUser = userRepository.save(updatedUser);
            ArrayList<User> responseUser = new ArrayList<>();
            responseUser.add(savedUser);
            return responseUser;
        } else {
            throw new Exception("User does not exist");
        }
    }

    @Override
    public ArrayList<User> delete(UUID user_id) throws Exception {
        log.info("Deleting user: {}", user_id);
        Optional<User> fetchedUser = userRepository.findById(user_id);
        if(fetchedUser.isPresent()){
            userRepository.deleteById(user_id);
            return new ArrayList<>();
        } else {
            throw new Exception("User does not exist");
        }
    }
}

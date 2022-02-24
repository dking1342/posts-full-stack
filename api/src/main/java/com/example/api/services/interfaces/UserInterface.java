package com.example.api.services.interfaces;

import com.example.api.models.schemas.User;

import java.util.ArrayList;
import java.util.UUID;

public interface UserInterface {
    ArrayList<User> get(String username) throws Exception;
    ArrayList<User> list() throws Exception;
    ArrayList<User> create(User user) throws Exception;
    ArrayList<User> update(UUID user_id, User user) throws Exception;
    ArrayList<User> delete(UUID user_id) throws Exception;
}

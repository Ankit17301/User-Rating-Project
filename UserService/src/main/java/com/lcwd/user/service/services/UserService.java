package com.lcwd.user.service.services;

import com.lcwd.user.service.entities.User;

import java.util.List;

public interface UserService {

    //user operations
    //create
    User saveUser(User user);

    //get all users
    List<User> getAllUsers();

    //get single user of given userId
    User getUser(String userId);

    //TODO: update user
    //TODO: delete user
}

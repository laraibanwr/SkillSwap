package com.skillswap.backend.service;

import com.skillswap.backend.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
}

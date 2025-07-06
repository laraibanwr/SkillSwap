package com.skillswap.backend.service.user;

import com.skillswap.backend.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    List<User> getUsersOfferingSkill(String skill);
    List<User> getUsersWantingSkill(String skill);
    List<User> getMutualMatches(Long userId);
    User updateUser(Long id, User updatedUser);
    void deleteUser(Long id);
    User getUserById(Long id);
    List<User> searchUsersByName(String name);
    void addFavorite(Long userId, Long favoriteUserId);
    void removeFavorite(Long userId, Long favoriteUserId);
    List<User> getFavorites(Long userId);

}

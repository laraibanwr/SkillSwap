package com.skillswap.backend.service.impl;

import com.skillswap.backend.entity.User;
import com.skillswap.backend.repository.UserRepository;
import com.skillswap.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired // constructor injection
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        // Check for duplicate email
        userRepository.findByEmail(user.getEmail())
                .ifPresent(existingUser -> {
                    throw new RuntimeException("Email already registered!");
                });

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersOfferingSkill(String skill) {
        return userRepository.findUsersOfferingSkill(skill);
    }

    @Override
    public List<User> getUsersWantingSkill(String skill) {
        return userRepository.findUsersWantingSkill(skill);
    }
    @Override
    public List<User> getMutualMatches(Long userId) {
        return userRepository.findMutualMatches(userId);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setSkillOffered(updatedUser.getSkillOffered());
            user.setSkillWanted(updatedUser.getSkillWanted());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}

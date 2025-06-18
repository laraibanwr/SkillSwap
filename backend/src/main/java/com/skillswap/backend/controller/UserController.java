package com.skillswap.backend.controller;

import com.skillswap.backend.entity.User;
import com.skillswap.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // allows frontend (React) to call backend
public class UserController {

    private final UserService userService;

    @Autowired // constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST /api/users - Create new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = userService.createUser(user);
        return ResponseEntity.ok(created);
    }

    // GET /api/users - Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // GET /api/users/offering?skill=java
    @GetMapping("/offering")
    public ResponseEntity<List<User>> getUsersOfferingSkill(@RequestParam String skill) {
        return ResponseEntity.ok(userService.getUsersOfferingSkill(skill));
    }

    // GET /api/users/wanting?skill=java
    @GetMapping("/wanting")
    public ResponseEntity<List<User>> getUsersWantingSkill(@RequestParam String skill) {
        return ResponseEntity.ok(userService.getUsersWantingSkill(skill));
    }

    // GET /api/users/matches?userId=1
    @GetMapping("/matches")
    public ResponseEntity<List<User>> getMutualMatches(@RequestParam Long userId) {
        return ResponseEntity.ok(userService.getMutualMatches(userId));
    }

    // PUT /api/users/{id} - Update user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    // DELETE /api/users/{id} - Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}

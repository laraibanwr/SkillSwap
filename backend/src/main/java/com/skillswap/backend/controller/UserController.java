package com.skillswap.backend.controller;

import com.skillswap.backend.entity.User;
import com.skillswap.backend.service.UserService;
import com.skillswap.backend.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
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

    // Unified search: by name, offered skill, or wanted skill
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(
            @RequestParam String type,
            @RequestParam String query) {

        switch (type.toLowerCase()) {
            case "name":
                return ResponseEntity.ok(userService.searchUsersByName(query));
            case "offered":
                return ResponseEntity.ok(userService.getUsersOfferingSkill(query));
            case "wanted":
                return ResponseEntity.ok(userService.getUsersWantingSkill(query));
            default:
                return ResponseEntity.badRequest().build();
        }
    }

    // GET /api/users/matches?userId=1
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/matches")
    public ResponseEntity<List<User>> getMutualMatches(@RequestParam Long userId) {
        return ResponseEntity.ok(userService.getMutualMatches(userId));
    }

    // PUT /api/users/{id} - Update user
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    // DELETE /api/users/{id} - Delete user (admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/users/{id} - Get user by ID
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // -------------------- Favorites (extracted userId from token) --------------------

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/favorites/{favoriteId}")
    public ResponseEntity<Void> addFavorite(@PathVariable Long favoriteId) {
        Long userId = AuthUtil.getCurrentUserId();  // ✅ Extracted from token
        userService.addFavorite(userId, favoriteId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/favorites/{favoriteId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long favoriteId) {
        Long userId = AuthUtil.getCurrentUserId();
        userService.removeFavorite(userId, favoriteId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/favorites")
    public ResponseEntity<List<User>> getFavorites() {
        Long userId = AuthUtil.getCurrentUserId();
        return ResponseEntity.ok(userService.getFavorites(userId));
    }
}

package com.skillswap.backend.controller;

import com.skillswap.backend.entity.ConnectionRequest;
import com.skillswap.backend.entity.User;
import com.skillswap.backend.service.connection.ConnectionRequestService;
import com.skillswap.backend.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/connections")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ConnectionRequestController {

    private final ConnectionRequestService connectionService;

    // Send a connection request
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/send")
    public ResponseEntity<Void> sendRequest(@RequestParam Long receiverId) {
        Long senderId = AuthUtil.getCurrentUserId(); // 🔐 from JWT
        connectionService.sendRequest(senderId, receiverId);
        return ResponseEntity.ok().build();
    }

    // Accept a connection request
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{requestId}/accept")
    public ResponseEntity<Void> acceptRequest(@PathVariable Long requestId) {
        connectionService.acceptRequest(requestId);
        return ResponseEntity.ok().build();
    }

    // Reject a connection request
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{requestId}/reject")
    public ResponseEntity<Void> rejectRequest(@PathVariable Long requestId) {
        connectionService.rejectRequest(requestId);
        return ResponseEntity.ok().build();
    }

    // Get all pending requests for logged-in user
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/pending")
    public ResponseEntity<List<ConnectionRequest>> getPendingRequests() {
        Long userId = AuthUtil.getCurrentUserId();
        return ResponseEntity.ok(connectionService.getPendingRequests(userId));
    }

    // Get all accepted connections (collaborators)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/accepted")
    public ResponseEntity<List<User>> getAcceptedConnections() {
        Long userId = AuthUtil.getCurrentUserId();
        return ResponseEntity.ok(connectionService.getAcceptedConnections(userId));
    }
}

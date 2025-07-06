package com.skillswap.backend.service.connection;

import com.skillswap.backend.entity.ConnectionRequest;
import com.skillswap.backend.entity.User;

import java.util.List;

public interface ConnectionRequestService {
    void sendRequest(Long senderId, Long receiverId);
    void acceptRequest(Long requestId);
    void rejectRequest(Long requestId);
    List<ConnectionRequest> getPendingRequests(Long userId);
    List<User> getAcceptedConnections(Long userId);
}

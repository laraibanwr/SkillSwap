package com.skillswap.backend.service.connection;

import com.skillswap.backend.entity.ConnectionRequest;
import com.skillswap.backend.entity.User;
import com.skillswap.backend.repository.ConnectionRequestRepository;
import com.skillswap.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ConnectionRequestServiceImpl implements ConnectionRequestService {

    private final ConnectionRequestRepository connectionRequestRepository;
    private final UserRepository userRepository;

    @Override
    public void sendRequest(Long senderId, Long receiverId) {
        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("You cannot send a request to yourself.");
        }

        boolean exists = connectionRequestRepository.existsBySenderIdAndReceiverId(senderId, receiverId);
        if (exists) {
            throw new RuntimeException("Connection request already exists.");
        }

        // Ensure both users exist
        userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        ConnectionRequest request = ConnectionRequest.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .status(ConnectionRequest.Status.PENDING)
                .timestamp(LocalDateTime.now())
                .build();

        connectionRequestRepository.save(request);
    }

    @Override
    public void acceptRequest(Long requestId) {
        ConnectionRequest request = connectionRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(ConnectionRequest.Status.ACCEPTED);
        connectionRequestRepository.save(request);
    }

    @Override
    public void rejectRequest(Long requestId) {
        ConnectionRequest request = connectionRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(ConnectionRequest.Status.REJECTED);
        connectionRequestRepository.save(request);
    }

    @Override
    public List<ConnectionRequest> getPendingRequests(Long userId) {
        return connectionRequestRepository.findByReceiverIdAndStatus(userId, ConnectionRequest.Status.PENDING);
    }

    @Override
    public List<User> getAcceptedConnections(Long userId) {
        List<ConnectionRequest> sent = connectionRequestRepository.findBySenderIdAndStatus(userId, ConnectionRequest.Status.ACCEPTED);
        List<ConnectionRequest> received = connectionRequestRepository.findByReceiverIdAndStatus(userId, ConnectionRequest.Status.ACCEPTED);

        List<User> connections = sent.stream()
                .map(req -> userRepository.findById(req.getReceiverId())
                        .orElse(null))
                .filter(user -> user != null)
                .collect(Collectors.toList());

        connections.addAll(received.stream()
                .map(req -> userRepository.findById(req.getSenderId())
                        .orElse(null))
                .filter(user -> user != null)
                .toList());

        return connections;
    }
}

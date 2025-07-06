package com.skillswap.backend.repository;

import com.skillswap.backend.entity.ConnectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {

    List<ConnectionRequest> findByReceiverIdAndStatus(Long receiverId, ConnectionRequest.Status status);

    List<ConnectionRequest> findBySenderIdAndStatus(Long senderId, ConnectionRequest.Status status);

    List<ConnectionRequest> findBySenderIdOrReceiverIdAndStatus(Long senderId, Long receiverId, ConnectionRequest.Status status);

    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);
}

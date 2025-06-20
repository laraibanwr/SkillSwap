package com.skillswap.backend.repository;

import com.skillswap.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // for checking duplicates

    @Query("SELECT u FROM User u JOIN u.skillOffered s WHERE LOWER(s) LIKE LOWER(CONCAT('%', :skill, '%'))")
    List<User> findUsersOfferingSkill(String skill);

    @Query("SELECT u FROM User u JOIN u.skillWanted s WHERE LOWER(s) LIKE LOWER(CONCAT('%', :skill, '%'))")
    List<User> findUsersWantingSkill(String skill);

    @Query("SELECT u FROM User u " +
            "JOIN u.skillOffered offered " +
            "JOIN u.skillWanted wanted " +
            "WHERE u.id != :userId " +
            "AND EXISTS (SELECT 1 FROM User u2 JOIN u2.skillWanted w WHERE u2.id = :userId AND LOWER(offered) IN (LOWER(w))) " +
            "AND EXISTS (SELECT 1 FROM User u3 JOIN u3.skillOffered o WHERE u3.id = :userId AND LOWER(wanted) IN (LOWER(o)))")
    List<User> findMutualMatches(Long userId);

    List<User> findByNameContainingIgnoreCase(String name);

}

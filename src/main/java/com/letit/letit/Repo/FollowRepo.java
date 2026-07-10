package com.letit.letit.Repo;

import com.letit.letit.Entity.Follow;
import com.letit.letit.Entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
public interface FollowRepo extends JpaRepository<Follow, FollowId> {
    List<Follow> findByFollowerIdAndStatus(String followerId, String status);
    List<Follow> findByFollowingIdAndStatus(String followingId, String status);
}

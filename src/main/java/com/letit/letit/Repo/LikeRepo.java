package com.letit.letit.Repo;

import com.letit.letit.Entity.Like;
import com.letit.letit.Entity.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepo extends JpaRepository<Like, String> {
    Optional<Like> findByBaseProfileIdAndTargetTypeAndTargetId(String userId, TargetType targetType, String targetId);
    long countByTargetTypeAndTargetId(TargetType targetType, String targetId);
    boolean existsByBaseProfileIdAndTargetTypeAndTargetId(String userId, TargetType targetType, String targetId);
}

package com.letit.letit.Repo;

import com.letit.letit.Entity.Follow;
import com.letit.letit.Entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepo extends JpaRepository<Follow, FollowId> {


}

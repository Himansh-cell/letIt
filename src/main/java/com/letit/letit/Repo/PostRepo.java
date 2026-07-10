package com.letit.letit.Repo;


import com.letit.letit.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.letit.letit.Entity.Visibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, String> {
    List<Post> findByBaseProfileId(String baseProfileId);
    Page<Post> findByBaseProfileIdInAndVisibilityNot(List<String> userIds, Visibility visibility, Pageable pageable);
    Page<Post> findByVisibilityNot(Visibility visibility, Pageable pageable);
}

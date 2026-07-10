package com.letit.letit.Repo;

import com.letit.letit.Entity.MultiMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultiMediaRepository extends JpaRepository<MultiMedia, String> {
    List<MultiMedia> findByPostId(String postId);
    void deleteByPostId(String postId);
}


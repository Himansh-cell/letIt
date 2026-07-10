package com.letit.letit.Repo;

import com.letit.letit.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, String> {
    List<Comment> findByPostIdAndParentCommentIsNull(String postId);
    List<Comment> findByParentCommentId(String parentCommentId);
}

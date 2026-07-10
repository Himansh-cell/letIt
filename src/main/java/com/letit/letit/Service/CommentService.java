package com.letit.letit.Service;

import com.letit.letit.Dto.CommentResponse;
import com.letit.letit.Entity.BaseProfile;
import com.letit.letit.Entity.Comment;
import com.letit.letit.Entity.Post;
import com.letit.letit.Repo.BaseProfileRepo;
import com.letit.letit.Repo.CommentRepo;
import com.letit.letit.Repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private BaseProfileRepo baseProfileRepo;

    public void createComment(String postId, String text, String username) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        BaseProfile baseProfile = baseProfileRepo.findByUserName(username).orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setBaseProfile(baseProfile);
        comment.setText(text);
        comment.setCreatedDate(LocalDate.now());
        comment.setLikesCount(0L);

        commentRepo.save(comment);

        post.setComments((post.getComments() == null ? 0 : post.getComments()) + 1);
        postRepo.save(post);
    }

    public void replyToComment(String parentCommentId, String text, String username) {
        Comment parent = commentRepo.findById(parentCommentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        BaseProfile baseProfile = baseProfileRepo.findByUserName(username).orElseThrow(() -> new RuntimeException("User not found"));

        Comment reply = new Comment();
        reply.setPost(parent.getPost());
        reply.setParentComment(parent);
        reply.setBaseProfile(baseProfile);
        reply.setText(text);
        reply.setCreatedDate(LocalDate.now());
        reply.setLikesCount(0L);

        commentRepo.save(reply);
        
        Post post = parent.getPost();
        post.setComments((post.getComments() == null ? 0 : post.getComments()) + 1);
        postRepo.save(post);
    }

    public void updateComment(String commentId, String text, String username) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        if (!comment.getBaseProfile().getUserName().equals(username)) {
            throw new RuntimeException("Not authorized");
        }
        comment.setText(text);
        commentRepo.save(comment);
    }

    public void deleteComment(String commentId, String username) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        if (!comment.getBaseProfile().getUserName().equals(username)) {
            throw new RuntimeException("Not authorized");
        }
        Post post = comment.getPost();
        post.setComments(Math.max(0, (post.getComments() == null ? 0 : post.getComments()) - 1));
        postRepo.save(post);
        commentRepo.delete(comment);
    }

    public List<CommentResponse> getCommentsByPost(String postId) {
        return commentRepo.findByPostIdAndParentCommentIsNull(postId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CommentResponse mapToResponse(Comment comment) {
        CommentResponse res = new CommentResponse();
        res.setId(comment.getId());
        res.setText(comment.getText());
        res.setUsername(comment.getBaseProfile().getUserName());
        res.setCreatedDate(comment.getCreatedDate());
        res.setLikesCount(comment.getLikesCount());
        if (comment.getParentComment() != null) {
            res.setParentCommentId(comment.getParentComment().getId());
        }
        return res;
    }
}

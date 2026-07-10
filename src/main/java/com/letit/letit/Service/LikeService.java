package com.letit.letit.Service;

import com.letit.letit.Entity.BaseProfile;
import com.letit.letit.Entity.Comment;
import com.letit.letit.Entity.Like;
import com.letit.letit.Entity.Post;
import com.letit.letit.Entity.TargetType;
import com.letit.letit.Repo.BaseProfileRepo;
import com.letit.letit.Repo.CommentRepo;
import com.letit.letit.Repo.LikeRepo;
import com.letit.letit.Repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private BaseProfileRepo baseProfileRepo;

    public void likePost(String postId, String username) {
        BaseProfile baseProfile = baseProfileRepo.findByUserName(username).orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        if (!likeRepo.existsByBaseProfileIdAndTargetTypeAndTargetId(baseProfile.getId(), TargetType.POST, postId)) {
            Like like = new Like();
            like.setBaseProfile(baseProfile);
            like.setTargetType(TargetType.POST);
            like.setTargetId(postId);
            likeRepo.save(like);

            post.setLikes((post.getLikes() == null ? 0 : post.getLikes()) + 1);
            postRepo.save(post);
        }
    }

    public void unlikePost(String postId, String username) {
        BaseProfile baseProfile = baseProfileRepo.findByUserName(username).orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        likeRepo.findByBaseProfileIdAndTargetTypeAndTargetId(baseProfile.getId(), TargetType.POST, postId).ifPresent(like -> {
            likeRepo.delete(like);
            post.setLikes(Math.max(0, (post.getLikes() == null ? 0 : post.getLikes()) - 1));
            postRepo.save(post);
        });
    }

    public void likeComment(String commentId, String username) {
        BaseProfile baseProfile = baseProfileRepo.findByUserName(username).orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!likeRepo.existsByBaseProfileIdAndTargetTypeAndTargetId(baseProfile.getId(), TargetType.COMMENT, commentId)) {
            Like like = new Like();
            like.setBaseProfile(baseProfile);
            like.setTargetType(TargetType.COMMENT);
            like.setTargetId(commentId);
            likeRepo.save(like);

            comment.setLikesCount((comment.getLikesCount() == null ? 0 : comment.getLikesCount()) + 1);
            commentRepo.save(comment);
        }
    }

    public void unlikeComment(String commentId, String username) {
        BaseProfile baseProfile = baseProfileRepo.findByUserName(username).orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));

        likeRepo.findByBaseProfileIdAndTargetTypeAndTargetId(baseProfile.getId(), TargetType.COMMENT, commentId).ifPresent(like -> {
            likeRepo.delete(like);
            comment.setLikesCount(Math.max(0, (comment.getLikesCount() == null ? 0 : comment.getLikesCount()) - 1));
            commentRepo.save(comment);
        });
    }
}

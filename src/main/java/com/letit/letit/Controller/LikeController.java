package com.letit.letit.Controller;

import com.letit.letit.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/post/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable String postId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        likeService.likePost(postId, username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/post/{postId}/like")
    public ResponseEntity<?> unlikePost(@PathVariable String postId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        likeService.unlikePost(postId, username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/comment/{commentId}/like")
    public ResponseEntity<?> likeComment(@PathVariable String commentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        likeService.likeComment(commentId, username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comment/{commentId}/like")
    public ResponseEntity<?> unlikeComment(@PathVariable String commentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        likeService.unlikeComment(commentId, username);
        return ResponseEntity.ok().build();
    }
}

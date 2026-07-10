package com.letit.letit.Controller;

import com.letit.letit.Dto.CommentRequest;
import com.letit.letit.Dto.CommentResponse;
import com.letit.letit.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<?> createComment(@PathVariable String postId, @RequestBody CommentRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        commentService.createComment(postId, request.getText(), username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/comment/{commentId}/reply")
    public ResponseEntity<?> replyToComment(@PathVariable String commentId, @RequestBody CommentRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        commentService.replyToComment(commentId, request.getText(), username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable String commentId, @RequestBody CommentRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        commentService.updateComment(commentId, request.getText(), username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable String commentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        commentService.deleteComment(commentId, username);
        return ResponseEntity.ok().build();
    }
}

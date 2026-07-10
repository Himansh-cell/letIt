package com.letit.letit.Controller;

import com.letit.letit.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import com.letit.letit.Dto.CreatePostRequest;
import com.letit.letit.Dto.PostResponse;
import com.letit.letit.Entity.Visibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestParam("file") MultipartFile file, @RequestParam("caption") String caption) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        postService.createPost(file, caption, username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(postService.getPostsByUsername(username));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable String postId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        postService.deletePost(postId, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/feed")
    public ResponseEntity<Page<PostResponse>> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(postService.getFeed(username, PageRequest.of(page, size, Sort.by("createdAt").descending())));
    }

    @PatchMapping("/{postId}/visibility")
    public ResponseEntity<?> setVisibility(@PathVariable String postId, @RequestParam Visibility visibility) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        postService.setPostVisibility(postId, visibility, username);
        return ResponseEntity.ok().build();
    }
}

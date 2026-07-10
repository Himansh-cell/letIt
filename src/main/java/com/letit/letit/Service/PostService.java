package com.letit.letit.Service;


import com.letit.letit.Entity.BaseProfile;
import com.letit.letit.Entity.Post;
import com.letit.letit.Repo.BaseProfileRepo;
import com.letit.letit.Repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import com.letit.letit.Entity.Visibility;
import com.letit.letit.Entity.Follow;
import com.letit.letit.Repo.FollowRepo;
import com.letit.letit.Dto.PostResponse;
import com.letit.letit.Entity.MultiMedia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private BaseProfileRepo baseProfileRepo;

    @Autowired
    private FollowRepo followRepo;

    @Autowired
    private MediaService mediaService;

    public void createPost(MultipartFile file, String caption, String username) {
        Post post = new Post();

        post.setLikes(0L);
        post.setComments(0L);
        post.setCaption(caption);
        post.setVisibility(Visibility.PUBLIC);
        post.setCreatedAt(LocalDateTime.now());

        BaseProfile baseProfile = baseProfileRepo.findByUserName(username)
                    .orElseThrow(() -> new RuntimeException(" not found"));

        post.setBaseProfile(baseProfile);

        Post savedPost = postRepo.save(post);
        try {
            mediaService.upload(savedPost.getId(), file);
        }catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public List<PostResponse> getPostsByUsername(String username) {
        BaseProfile baseProfile = baseProfileRepo.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return postRepo.findByBaseProfileId(baseProfile.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deletePost(String postId, String username) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.getBaseProfile().getUserName().equals(username)) {
            throw new RuntimeException("Not authorized");
        }
        if (post.getMedia() != null) {
            for (MultiMedia media : post.getMedia()) {
                try {
                    mediaService.delete(media.getId());
                } catch (IOException e) {
                    System.out.println("Error deleting media: " + e.getMessage());
                }
            }
        }
        postRepo.delete(post);
    }

    public Page<PostResponse> getFeed(String username, Pageable pageable) {
        BaseProfile baseProfile = baseProfileRepo.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<String> followingIds = followRepo.findByFollowerIdAndStatus(baseProfile.getId(), "FOLLOWING")
                .stream().map(f -> f.getFollowing().getId()).collect(Collectors.toList());
        
        followingIds.add(baseProfile.getId()); // Include own posts

        Page<Post> posts = postRepo.findByBaseProfileIdInAndVisibilityNot(followingIds, Visibility.ONLY_ME, pageable);
        return posts.map(this::mapToResponse);
    }

    public void setPostVisibility(String postId, Visibility visibility, String username) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.getBaseProfile().getUserName().equals(username)) {
            throw new RuntimeException("Not authorized");
        }
        post.setVisibility(visibility);
        postRepo.save(post);
    }

    private PostResponse mapToResponse(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setCaption(post.getCaption());
        response.setUsername(post.getBaseProfile().getUserName());
        response.setLikesCount(post.getLikes());
        response.setCommentsCount(post.getComments());
        response.setVisibility(post.getVisibility());
        response.setCreatedAt(post.getCreatedAt());
        if (post.getMedia() != null) {
            response.setMediaUrls(post.getMedia().stream().map(MultiMedia::getId).collect(Collectors.toList()));
        }
        return response;
    }
}
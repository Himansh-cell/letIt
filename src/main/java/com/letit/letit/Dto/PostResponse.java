package com.letit.letit.Dto;

import com.letit.letit.Entity.Visibility;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponse {
    private String id;
    private String caption;
    private String username;
    private Long likesCount;
    private Long commentsCount;
    private Visibility visibility;
    private List<String> mediaUrls;
    private LocalDateTime createdAt;
}

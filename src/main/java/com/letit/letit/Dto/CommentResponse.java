package com.letit.letit.Dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CommentResponse {
    private String id;
    private String text;
    private String username;
    private LocalDate createdDate;
    private String parentCommentId;
    private Long likesCount;
}

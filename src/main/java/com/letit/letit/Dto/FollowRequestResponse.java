package com.letit.letit.Dto;

import lombok.Data;

@Data
public class FollowRequestResponse {
    private String followerUsername;
    private String followingUsername;
    private String status;
}

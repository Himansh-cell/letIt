package com.letit.letit.Controller;

import com.letit.letit.Entity.BaseProfile;
import com.letit.letit.Entity.Follow;
import com.letit.letit.Entity.FollowId;
import com.letit.letit.Dto.FollowRequestResponse;
import com.letit.letit.Repo.BaseProfileRepo;
import com.letit.letit.Repo.FollowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private BaseProfileRepo baseProfileRepo;

    @Autowired
    private FollowRepo followRepo;

    @PostMapping("/{receiver_username}")
  public ResponseEntity<String> sendRequest(@PathVariable String receiver_username){
      String senderUsername =  SecurityContextHolder.getContext().getAuthentication().getName()  ;

      if(receiver_username.equals(senderUsername)){
          return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }



        BaseProfile sender = baseProfileRepo.findByUserName(senderUsername)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        BaseProfile receiver = baseProfileRepo.findByUserName(receiver_username)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Follow follow = new Follow();

        follow.setId(new FollowId(sender.getId(), receiver.getId()));
        follow.setFollower(sender);
        follow.setFollowing(receiver);
        follow.setStatus("PENDING");

        followRepo.save(follow);

return ResponseEntity.ok("friend request sent successfully");
    }


    @PutMapping("/{sender_username}/accept")
    public ResponseEntity<String> acceptRequest(@PathVariable String sender_username){
        String receiverUsername =  SecurityContextHolder.getContext().getAuthentication().getName()  ;

        if(sender_username.equals(receiverUsername)){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        BaseProfile sender = baseProfileRepo.findByUserName(sender_username)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        BaseProfile receiver = baseProfileRepo.findByUserName(receiverUsername)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));


        FollowId followId = new FollowId(sender.getId(), receiver.getId());

        Follow f1 = followRepo.findById(followId).orElseThrow(() -> new RuntimeException("Follow not found"));

        f1.setStatus("FOLLOWING");


        followRepo.save(f1);

        return ResponseEntity.ok("friend request accepted successfully");
    }

    @GetMapping("/requests")
    public ResponseEntity<List<FollowRequestResponse>> myRequests() {
        String receiverUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        BaseProfile receiver = baseProfileRepo.findByUserName(receiverUsername)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        List<FollowRequestResponse> requests = followRepo.findByFollowingIdAndStatus(receiver.getId(), "PENDING")
                .stream()
                .map(follow -> {
                    FollowRequestResponse response = new FollowRequestResponse();
                    response.setFollowerUsername(follow.getFollower().getUserName());
                    response.setFollowingUsername(follow.getFollowing().getUserName());
                    response.setStatus(follow.getStatus());
                    return response;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(requests);
    }

}

package com.letit.letit.Controller;

import com.letit.letit.Entity.BaseProfile;
import com.letit.letit.Entity.Profile;
import com.letit.letit.Repo.BaseProfileRepo;
import com.letit.letit.Repo.ProfileRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/profile")
public class ProfileController {

    @Autowired
    private BaseProfileRepo baseProfileRepo;

    @Autowired
    private ProfileRepo profileRepo;

    @PostMapping
    public ResponseEntity<?> createProfile( @RequestBody Profile profile) {
       String username = SecurityContextHolder.getContext().getAuthentication().getName();
       BaseProfile baseProfile = baseProfileRepo.findByUserName(username)
               .orElseThrow(() -> new RuntimeException("User not found"));
       profile.setBaseProfile(baseProfile);
       profile.setId(baseProfile.getId());
       profileRepo.save(profile);
       return ResponseEntity.status(HttpStatus.CREATED).build();
    }



}

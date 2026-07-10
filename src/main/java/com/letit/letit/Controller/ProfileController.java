package com.letit.letit.Controller;

import com.letit.letit.Entity.BaseProfile;
import com.letit.letit.Entity.Profile;
import com.letit.letit.Repo.BaseProfileRepo;
import com.letit.letit.Repo.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

       profileRepo.save(profile);
       return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping ("/{username}")
    public ResponseEntity<?> showProfile( @PathVariable String username) {


    }
}

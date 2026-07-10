package com.letit.letit.Service;

import com.letit.letit.Entity.BaseProfile;
import com.letit.letit.Repo.BaseProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private BaseProfileRepo baseProfileRepo;

    public void makePublic(String username) {
        BaseProfile profile = baseProfileRepo.findByUserName(username).orElseThrow(() -> new RuntimeException("User not found"));
        profile.setStatusPublic(true);
        baseProfileRepo.save(profile);
    }

    public void makePrivate(String username) {
        BaseProfile profile = baseProfileRepo.findByUserName(username).orElseThrow(() -> new RuntimeException("User not found"));
        profile.setStatusPublic(false);
        baseProfileRepo.save(profile);
    }
}

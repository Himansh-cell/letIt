package com.letit.letit.Controller;

import com.letit.letit.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PatchMapping("/public")
    public ResponseEntity<?> makePublic() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        accountService.makePublic(username);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/private")
    public ResponseEntity<?> makePrivate() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        accountService.makePrivate(username);
        return ResponseEntity.ok().build();
    }
}

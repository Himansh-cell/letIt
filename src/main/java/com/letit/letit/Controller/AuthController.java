package com.letit.letit.Controller;

import com.letit.letit.Dto.JwtResponse;
import com.letit.letit.Dto.RegisterBody;
import com.letit.letit.Entity.BaseProfile;
import com.letit.letit.Entity.Role;
import com.letit.letit.Repo.BaseProfileRepo;
import com.letit.letit.Repo.RoleRepo;
import com.letit.letit.Security.CustomUserDetailsService;
import com.letit.letit.Security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private BaseProfileRepo baseProfileRepo;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder  passwordEncoder;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
      try{  authenticationManager.authenticate(token);}
      catch (BadCredentialsException e){
          return ResponseEntity.badRequest().build();
      }



        String JwtToken = jwtHelper.generateToken(customUserDetailsService.loadUserByUsername(email));

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(JwtToken);

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody RegisterBody registerBody) {
        BaseProfile baseProfile = new BaseProfile();
        baseProfile.setUserName(registerBody.getUserName());
        baseProfile.setEmail(registerBody.getEmail());
        baseProfile.setPassword(passwordEncoder.encode(registerBody.getPassword()));


        Role role = roleRepo.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        baseProfile.setRoles(List.of(role));
        baseProfile.setActive(true);
        baseProfile.setStatusPublic(true);
        baseProfile.setCreatedAt(LocalDate.now());

        baseProfileRepo.save(baseProfile);

        return ResponseEntity.ok("Successfully registered");
    }


}

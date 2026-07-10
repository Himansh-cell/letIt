package com.letit.letit.Security;

import com.letit.letit.Entity.BaseProfile;
import com.letit.letit.Entity.Role;
import com.letit.letit.Repo.BaseProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private BaseProfileRepo baseProfileRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<BaseProfile> obj = baseProfileRepo.findByEmail(username);

        if (obj.isPresent()) {

            List<String> roleNames = obj.get().getRoles()
                    .stream()
                    .map(Role::getName)
                    .toList();


            return User.withUsername(obj.get().getUserName())
                    .password(obj.get().getPassword())
                    .authorities(roleNames.toArray(new String[0]))
                    .build();
        }
        else {
            throw new UsernameNotFoundException(username);
        }


    }
}

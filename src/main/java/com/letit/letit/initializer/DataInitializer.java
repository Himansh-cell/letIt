package com.letit.letit.initializer;

import com.letit.letit.Entity.Role;
import com.letit.letit.Repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepo roleRepo;

    @Override
    public void run(String... args) {

        if (!roleRepo.existsByName("USER")) {
            Role userRole = new Role();
            userRole.setName("USER");
            roleRepo.save(userRole);
        }

        if (!roleRepo.existsByName("PREMIUM")) {
            Role adminRole = new Role();
            adminRole.setName("PREMIUM");
            roleRepo.save(adminRole);
        }
    }
}

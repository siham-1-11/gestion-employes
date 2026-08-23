package com.projet.gestionEtudiant.config;

import com.projet.gestionEtudiant.entity.Role;
import com.projet.gestionEtudiant.entity.User;
import com.projet.gestionEtudiant.repository.RoleRepository;
import com.projet.gestionEtudiant.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));
        
        Role userRole = roleRepository.findByName("ROLE_EMPLOYEE")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_EMPLOYEE")));

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEnabled(true);
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);
        }
    }
}
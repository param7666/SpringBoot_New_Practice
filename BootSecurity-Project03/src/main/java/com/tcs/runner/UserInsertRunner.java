package com.tcs.runner;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.tcs.entity.Role;
import com.tcs.entity.User;
import com.tcs.repository.RoleRepository;
import com.tcs.repository.UserRepository;

//@Component
public class UserInsertRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // 1. Seed roles first, if not already present
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

        Role librarianRole = roleRepository.findByName("ROLE_LIBRARIAN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_LIBRARIAN")));

        Role memberRole = roleRepository.findByName("ROLE_MEMBER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_MEMBER")));

        // 2. Seed one user per role, only if username doesn't already exist

        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@library.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            admin.setRoles(roles);
            userRepository.save(admin);
        }

        if (!userRepository.existsByUsername("librarian")) {
            User librarian = new User();
            librarian.setUsername("librarian");
            librarian.setEmail("librarian@library.com");
            librarian.setPassword(passwordEncoder.encode("librarian123"));
            Set<Role> roles = new HashSet<>();
            roles.add(librarianRole);
            librarian.setRoles(roles);
            userRepository.save(librarian);
        }

        if (!userRepository.existsByUsername("member")) {
            User member = new User();
            member.setUsername("member");
            member.setEmail("member@library.com");
            member.setPassword(passwordEncoder.encode("member123"));
            Set<Role> roles = new HashSet<>();
            roles.add(memberRole);
            member.setRoles(roles);
            userRepository.save(member);
        }
    }
}
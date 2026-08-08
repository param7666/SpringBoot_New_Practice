package com.tcs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tcs.entity.Role;
import com.tcs.entity.User;
import com.tcs.repository.RoleRepository;
import com.tcs.repository.UserRepository;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Map<String, Object> map) {
        map.put("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role memberRole = roleRepository.findByName("ROLE_MEMBER")
                .orElseThrow(() -> new RuntimeException("Default role not found. Did you seed roles?"));

        Set<Role> roles = new HashSet<>();
        roles.add(memberRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "redirect:/login?registered";
    }
    
    @GetMapping("/access-denied")
    public String accessDeniedPage() {
        return "auth/access-denied";
    }
}

package com.param.runner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.param.entity.User;
import com.param.repository.UserRepository;

import lombok.RequiredArgsConstructor;

//@Component 
@RequiredArgsConstructor 
public class LoadUser implements CommandLineRunner {

    private final Logger log=LoggerFactory.getLogger(LoadUser.class);
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
       
        List<User> users = List.of(
        User.builder().name("Rahul Sharma").username("rahul").role("USER").password(passwordEncoder.encode("password")).build(),
        User.builder().name("Priya Singh").username("priya").role("USER").password(passwordEncoder.encode("password")).build(),
        User.builder().name("Amit Kumar").username("amit").role("ADMIN").password(passwordEncoder.encode("password")).build(),
        User.builder().name("Neha Patel").username("neha").role("USER").password(passwordEncoder.encode("password")).build(),
        User.builder().name("Vikram Joshi").username("vikram").role("ADMIN").password(passwordEncoder.encode("password")).build()
        );

        try {
            repo.saveAll(users);

            log.info("All users saved into db");
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
       
    }

    
    
}
package com.param.runner;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.param.entity.User;
import com.param.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Component 
@RequiredArgsConstructor 
@Slf4j 
public class UserLoad implements  CommandLineRunner{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(UserLoad.class);

    @Override
    public void run(String... args) throws Exception {
       User user1 = new User("Param", "param", passwordEncoder.encode("password"), "USER");
       User user2 = new User("Sundar", "sundar", passwordEncoder.encode("password"), "ADMIN");
       try {
        userRepository.save(user1);
        userRepository.save(user2);
       } catch (Exception e) {
        logger.error("Error while saving users: " + e.getMessage());
       }

       logger.info("Users saved successfully.");
        
    }

}

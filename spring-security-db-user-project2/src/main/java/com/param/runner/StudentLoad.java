package com.param.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.param.entity.Student;
import com.param.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class StudentLoad implements CommandLineRunner{

    private final StudentRepository studentRepository;
    private final PasswordEncoder encoder;

    private final Logger log=LoggerFactory.getLogger(StudentLoad.class);

    @Override
    public void run(String... args) throws Exception {
        Student s1= new Student("Param", "param",encoder.encode("password") , "STUDENT");
        Student s2= new Student("Sundar", "sundar", encoder.encode("password"), "ADMIN");

        try {
            studentRepository.save(s1);
            studentRepository.save(s2);
            log.debug("STUDENT OBJECTS SAVED INTO DATABASE");
        } catch (Exception e) {
            log.error("Error while saving STUDENT OBJECT {}", e.getMessage());
        }

    }

    

}

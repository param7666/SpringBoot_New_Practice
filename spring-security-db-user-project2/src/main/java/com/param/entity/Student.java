package com.param.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity 
@Table(name="students101")
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@RequiredArgsConstructor 
public class Student implements UserDetails{

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    @NonNull 
    private String name;

    @Column (nullable = false,unique = true)
    @NonNull 
    private String username;

    @Column (nullable = false)
    @NonNull 
    private String password;

    @Column (nullable = false)
    @NonNull 
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role));
    }

    @Override 
    public String getUsername(){
        return username;
    }

    @Override 
    public String getPassword(){
        return password;
    }



}

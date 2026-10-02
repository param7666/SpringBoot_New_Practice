package com.param.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.param.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration 
@RequiredArgsConstructor 
public class SecurityConfig {

    private final UserRepository repo;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean 
    public UserDetailsService userDetailsService(){
        return username->repo.findByUsername(username)
                        .orElseThrow(()->new UsernameNotFoundException("Invalid Username"));
    }

    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider){
        return authentication -> authenticationProvider.authenticate(authentication);
    }

    @Bean 
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder){
       DaoAuthenticationProvider provider=new DaoAuthenticationProvider(userDetailsService);
       provider.setPasswordEncoder(passwordEncoder);
       return provider;
    }

    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.csrf(scrf->scrf.disable())
        .cors(Customizer.withDefaults())
        .authorizeHttpRequests(req-> req
            .requestMatchers("/login/**","/swagger-ui/**","/v3/api-docs").permitAll()
            .requestMatchers("/users/**").hasRole("USER")
            .requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults());

            return http.build();
            

    }


}

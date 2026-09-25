package com.param.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
@EnableWebSecurity 
@EnableMethodSecurity 
public class SecurityConfig {
    
    @Bean 
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails param= User.builder().username("Param")
                .password(passwordEncoder().encode("param123"))
                .roles("USER").build();
            
                UserDetails sundar=User.builder()
                .username("Sundar")
                .password(passwordEncoder().encode("sundar1234"))
                .roles("ADMIN").build();

                return new InMemoryUserDetailsManager(param,sundar);
    }


    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
        csrf(csrf->csrf.disable())
        .authorizeHttpRequests(req-> req.requestMatchers("/swagger-ui/**","/v3/api-docs/**","/index.html").permitAll()
        .requestMatchers("/user/**").hasRole("USER")
        .requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated())
        .formLogin(login->login.permitAll())
        .httpBasic(Customizer.withDefaults());
        return http.build();
    }


}

package com.tcs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth->
		auth.requestMatchers("/","/login**").permitAll() // those are the public pages allow all
		.anyRequest().authenticated() // other pages should be authenticated
		).oauth2Login(oauth2->oauth2.defaultSuccessUrl("/profile",true)); // where to land after login
		
		return http.build();
	}
}

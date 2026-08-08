package com.tcs.confg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.tcs.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity // enables @PreAuthorize / @Secured on service methods
public class SecurityConfig {

	private final CustomUserDetailsService cs;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider(cs);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
		.authorizeHttpRequests(auth-> auth.requestMatchers("/register", "/css/**", "/js/**").permitAll()
				.requestMatchers("/book/delete/**").hasRole("ADMIN")
				.requestMatchers("/book/new", "/book/edit/**", "/book/update/**").hasAnyRole("ADMIN", "LIBRARIAN")
				.requestMatchers("/book/**").hasAnyRole("ADMIN", "LIBRARIAN", "MEMBER")
				.anyRequest().authenticated())
		.formLogin(form->form.loginPage("/login").defaultSuccessUrl("/book",true).permitAll())
		.logout(logout->logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll())
		.exceptionHandling(exception -> exception.accessDeniedPage("/access-denied"))
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).maximumSessions(1))
		.authenticationProvider(authenticationProvider());
		return http.build();
	}
	
}

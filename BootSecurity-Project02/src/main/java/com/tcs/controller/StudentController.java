package com.tcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tcs.dto.StudentDTO;
import com.tcs.entity.Student;
import com.tcs.service.StudentService;

@Controller
public class StudentController {

	@Autowired
	private StudentService service;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@GetMapping("/register")
	public String openRegisterPage(Model model) {
		System.out.println("StudentController.openRegisterPage()");
		model.addAttribute("student",new StudentDTO());
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute("student") StudentDTO dto) {
		System.out.println("StudentController.register()");
		Student s=new Student();
		s.setName(dto.getName());
		s.setEmail(dto.getEmail());
		s.setPassword(encoder.encode(dto.getPassword()));
		s.setRole("ADMIN");
		Student rgs=service.register(s);
		
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		System.out.println("StudentController.loginPage()");
		return "/login";
		
	}
	
	@GetMapping("/home")
	public String login(@AuthenticationPrincipal Student s,Model model) {
		model.addAttribute("name", s.getName());
        model.addAttribute("role", s.getRole());
        return "home";
	}
	
	@GetMapping("/admin")
	public String adminPage(@AuthenticationPrincipal Student s, Model model) {
	    model.addAttribute("name", s.getName());
	    model.addAttribute("students", service.findAllStudent()); // if you want the table
	    return "admin";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied() {
	    return "access-denied";
	}
}

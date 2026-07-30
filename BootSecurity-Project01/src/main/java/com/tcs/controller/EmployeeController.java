package com.tcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tcs.DTO.EmployeeDTO;
import com.tcs.entity.Employee;
import com.tcs.repository.EmployeeRepo;

@Controller
public class EmployeeController {
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private EmployeeRepo repo;

	@GetMapping("/register") 
	public String showRegsiterForm(Model model) {
		model.addAttribute("employee",new EmployeeDTO());
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute("employee") EmployeeDTO dto) {
		Employee e=new Employee();
		e.setName(dto.getName());
		e.setEmail(dto.getEmail());
		e.setPassword(encoder.encode(dto.getPassword()));
		e.setRole("EMPLOYEE");
		repo.save(e);
		return "redirect:/login";
	}
	
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    
    @GetMapping("/home")
    public String home(@AuthenticationPrincipal Employee employee, Model model) {
        model.addAttribute("name", employee.getName());
        model.addAttribute("role", employee.getRole());
        return "home";
    }
}

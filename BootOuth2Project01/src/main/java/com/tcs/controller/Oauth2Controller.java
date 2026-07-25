package com.tcs.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Oauth2Controller {

	@GetMapping("/")
	public String home() {
		return "home"; // public home page to show login button
	}
	
	@GetMapping("/profile")
	public String profile(@AuthenticationPrincipal OAuth2User principle, Model model) {
		
		model.addAttribute("name",principle.getAttribute("name"));
		model.addAttribute("email",principle.getAttribute("email"));
		model.addAttribute("picture",principle.getAttribute("picture"));
		
		return "profile";
	}
}

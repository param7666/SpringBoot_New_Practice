package com.tcs.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OauthController {

	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/profile")
	public String profile(@AuthenticationPrincipal OAuth2User principle, Model model) {
		
		model. addAttribute("name", principle.getAttribute("name"));
		model. addAttribute("email", principle.getAttribute("email"));
		model. addAttribute("picture", principle.getAttribute("picture"));
		System.out.println((String)principle.getAttribute("name"));
		System.out.println((String)principle.getAttribute("email"));

		return "profile";
	}
	
}

package com.tcs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tcs.service.*;

import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/read")
public class PdfController {

	
	private final PdfService service;
	
    @GetMapping("/")
    public String showUploadForm() {
        return "upload";
    }

    @PostMapping
    public String handelUpload(@RequestParam("file") MultipartFile file, Model model) {
    	if(file.isEmpty()) {
    		 model.addAttribute("error", "Please select a PDF file.");
             return "upload";
    	}
    	try {
    		String text=service.extractText(file);
    		System.out.println("Extracted Text:\n" + text);
    		model.addAttribute("text", text);
    	} catch(Exception e) {
    		model.addAttribute("error", e.getMessage());
    	}
    	return "upload"; 
    }
    
}

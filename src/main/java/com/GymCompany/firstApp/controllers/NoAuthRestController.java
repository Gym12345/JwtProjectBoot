package com.GymCompany.firstApp.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
@RestController
public class NoAuthRestController {
	
	@GetMapping(value = "/favicon.ico")
	public void favicon(HttpServletRequest request) {
	}
		

}

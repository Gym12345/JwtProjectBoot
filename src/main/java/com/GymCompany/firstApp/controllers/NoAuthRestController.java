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
	
//	@GetMapping(value = "/getSecurityContext")
//    public void getSecurityContext(HttpServletRequest request) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            System.out.println("User Authentication Details: " + authentication.getDetails());
//            System.out.println("User Authorities: " + authentication.getAuthorities());
//            System.out.println("User Principal: " + authentication.getPrincipal());
//        } else {
//            System.out.println("No user is authenticated.");
//        }
//    }
	
//	@GetMapping(value = "/sendTokenInHeader")
//	public void sendTokenInHeader(HttpServletRequest request) {
//		System.out.println("sednheader");
//		
//		
//	}
}

package com.GymCompany.firstApp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



@RequestMapping("/admin")
@RestController
public class AdminRestController {
	
	 @Autowired
	 private RestTemplate restTemplate;


	  
	  
//	  @PostMapping("/run-notebook")   //rest request after authentication
//	    public String receiveData(@RequestBody String payload) {
//	      
//	        String receivedData = payload;
//	        System.out.println("Received data: " + receivedData);
//	        
//	       
//	        return "Received: " + receivedData;
//	    }
//	  
	  @PostMapping("/run-notebook")
	    public String testAIConnection(@RequestBody String payload) {
	        
		  
	        String url = "http://localhost:5000/admin/run-notebook";
	        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
	        
	        return response.getBody();
	    }
}

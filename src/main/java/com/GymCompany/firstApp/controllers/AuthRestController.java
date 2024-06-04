package com.GymCompany.firstApp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GymCompany.firstApp.jwt.JwtTokenProvider;
import com.GymCompany.firstApp.model.SignInResultDTO;
import com.GymCompany.firstApp.model.SignUpResultDTO;
import com.GymCompany.firstApp.model.UserListDTO;
import com.GymCompany.firstApp.service.SignService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/auth")
public class AuthRestController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(SwaggerController.class);
	  
	@Autowired
    private   SignService signService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
	
	
	  @PostMapping(value = "/loginCheck")
	    public String loginCheck(HttpServletRequest request, HttpServletResponse response,
	                             @RequestParam("userId") String userId,
	                             @RequestParam("userPw") String userPw) {

	        LOGGER.info("[signIn] Attempting to log in. id : {}, pw : ****", userId);
	        SignInResultDTO signInResultDTO = signService.signIn(userId, userPw);
	        
	        if (signInResultDTO.getCode() == 1) {
	            LOGGER.info("[signIn] Successfully logged in. id : {}, token : {}", userId, signInResultDTO.getToken());
	            
	            // Get JWT token from signInResultDTO
	            String jwtToken = signInResultDTO.getToken();
	            
	            if (jwtTokenProvider.validateToken(jwtToken)) {
	                
	                Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
	               
	                System.out.println("jwtToken:"+jwtToken);
	                System.out.println("authentication:"+authentication);
	                
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	                
	                //response.setHeader("X-AUTH-TOKEN",jwtToken );
	                

	    	        
	    	       
	                return jwtToken;
	            } else {
	                LOGGER.info("JWT token validation failed for user id: {}", userId);
	                return "JWT token validation failed";
	            }
	        } else {
	            LOGGER.info("Login failed for user id: {}", userId);
	            return "Login failed";
	        }
	    }
	  
	  
	  
	  @PostMapping(value = "/registerCheck")
	    public SignUpResultDTO registerCheck(@RequestBody UserListDTO userDTO) { //, @RequestParam(value="role") String role
		  
	        String role="normal"; //admin 만 아니면 어떤 스트링이던지 상관없음
	        System.out.println("userId:" + userDTO.getUserId());
	        SignUpResultDTO signUpResultDTO = signService.signUp(userDTO.getUserId(), userDTO.getUserPw(), userDTO.getUserName(), role);

	       LOGGER.info("[signUp] 회원가입을 완료했습니다. id : {}", userDTO.getUserId());
	        return signUpResultDTO;
	    }
	    
	  
	  
		@PostMapping("/rddCheck")
	    public int rddUserId(@RequestBody String userIdData) { 
	        System.out.println("Data received from client: " + userIdData);
	        int result = 0;
	        try {
	            result = signService.redundancyCheck(userIdData);
	            System.out.println("rddResult:" + result);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return result;
	    }


	
}

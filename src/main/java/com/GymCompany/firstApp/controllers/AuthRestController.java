package com.GymCompany.firstApp.controllers;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GymCompany.firstApp.jwt.JwtTokenProvider;
import com.GymCompany.firstApp.model.SignInResultDTO;
import com.GymCompany.firstApp.model.SignUpResultDTO;
import com.GymCompany.firstApp.model.UserListDTO;
import com.GymCompany.firstApp.service.SignService;
import com.GymCompany.firstApp.service.TokenBlacklistService;

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
	
    @Autowired
    private TokenBlacklistService tokenBlacklistService;
	
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
	               ;
	                return null;
	            }
	        } else {
	            LOGGER.info("Login failed for user id: {}", userId);
	            
                return null;
	        }
	    }
	  
	  
	  
	  @PostMapping(value = "/registerCheck")
	  public SignUpResultDTO registerCheck(@RequestBody UserListDTO userDTO) {
	      String role = "normal"; // admin 만 아니면 어떤 스트링이던지 상관없음
	      System.out.println("userId:" + userDTO.getUserId());

	      // Call the signUp method with all the necessary fields
	      SignUpResultDTO signUpResultDTO = signService.signUp(
	          userDTO.getUserId(),
	          userDTO.getUserPw(),
	          userDTO.getUserName(),
	          role,
	          userDTO.getEmail(),
	          userDTO.getPhone(),
	          userDTO.getAge(),
	          userDTO.getGender()
	      );

	      LOGGER.info("[signUp] 회원가입을 완료했습니다. id : {}", userDTO.getUserId());
	      return signUpResultDTO;
	  }

	  
	  
		@PostMapping("/rddCheck")
	    public int rddUserId(@RequestBody String userIdData) { 
			 System.out.println("rdddddddddddddddddddddd");
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
		
		 @PostMapping("/logout")
		    public int logout(HttpServletRequest request, @RequestHeader("token-for-blacklist") String token) {
			 	int result=0;
		        try {
		           

		            if (jwtTokenProvider.validateToken(token)) {// 아직 유효한 토큰이라면 ~~
		                // Blacklist the token
		            	Date tokenExpDate=jwtTokenProvider.getExpirationDate(token);
		            	tokenBlacklistService.blacklistToken(token,tokenExpDate);

		                // Clear the security context
		                SecurityContextHolder.clearContext();
		                result=1;
		                return result;
		            } else {
		                return result;
		            }
		        } catch (Exception e) {
		        	LOGGER.info("error while logging out:" + e.getMessage());
		            return result;
		        }
		    }
	
}

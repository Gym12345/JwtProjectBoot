package com.GymCompany.firstApp.service;

import com.GymCompany.firstApp.model.SignInResultDTO;
import com.GymCompany.firstApp.model.SignUpResultDTO;
import com.GymCompany.firstApp.model.TempUserDTO;
import com.GymCompany.firstApp.model.UserListDTO;

public interface SignService {
	SignUpResultDTO signUp(String userId,String userPw, String userName, String role);
	SignInResultDTO signIn(String userId,String userPw);
	
	int redundancyCheck(String userId);
}

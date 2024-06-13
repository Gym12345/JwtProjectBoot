package com.GymCompany.firstApp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.GymCompany.firstApp.model.UserListDTO;


@Repository
public interface UserListRepository extends JpaRepository<UserListDTO, Integer> { 
	
	UserListDTO getByUserId(String id);
	
	
}
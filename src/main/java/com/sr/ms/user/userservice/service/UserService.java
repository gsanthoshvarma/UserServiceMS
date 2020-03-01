package com.sr.ms.user.userservice.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sr.ms.user.userservice.service.model.UserDto;

public interface UserService extends UserDetailsService{
	
	public UserDto createUser(UserDto userDto);
	
	public UserDto getUserDetailsByEmail(String email);

}

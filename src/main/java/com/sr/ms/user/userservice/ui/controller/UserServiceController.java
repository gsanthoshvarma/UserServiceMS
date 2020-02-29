package com.sr.ms.user.userservice.ui.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sr.ms.user.userservice.service.UserService;
import com.sr.ms.user.userservice.service.model.UserDto;
import com.sr.ms.user.userservice.ui.model.CreateUserRequestModel;

import io.swagger.annotations.ApiModel;

@RestController
@ApiModel(description="UserSericeController used for singUp and singIn to application user.")
public class UserServiceController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/status/check")
	public String userDetails() {

		return "Hello UserService MS "+environment.getProperty("local.server.port");
	}
	
	@PostMapping(value="/signup", produces= {"application/json"},consumes= {"application/json"})
	public ResponseEntity<?> singUp(@Valid @RequestBody CreateUserRequestModel userDetails){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = userService.createUser(modelMapper.map(userDetails, UserDto.class));
		return new ResponseEntity<UserDto>(userDto,HttpStatus.CREATED);
	}
		
	}

	



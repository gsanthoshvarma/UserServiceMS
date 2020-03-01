package com.sr.ms.user.userservice.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sr.ms.user.userservice.data.UserEntity;
import com.sr.ms.user.userservice.data.UserRepository;
import com.sr.ms.user.userservice.service.model.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {
		userDto.setId(UUID.randomUUID().variant());
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = mapper.map(userDto, UserEntity.class);
		userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		UserEntity entity = userRepository.save(userEntity);
		return mapper.map(entity, UserDto.class);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> userEntity = userRepository.findByEmail(username);
		if (!userEntity.isPresent())
			throw new BadCredentialsException("Username and password invaild");
		return new User(username, userEntity.get().getPassword(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		Optional<UserEntity> userEntity = userRepository.findByEmail(email);
		if (!userEntity.isPresent())
			throw new BadCredentialsException("Username and password invaild");
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = mapper.map(userEntity.get(), UserDto.class);
		return userDto;
	}

}

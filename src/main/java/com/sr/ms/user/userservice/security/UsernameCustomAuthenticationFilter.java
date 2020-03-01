package com.sr.ms.user.userservice.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sr.ms.user.userservice.service.UserService;
import com.sr.ms.user.userservice.service.model.UserDto;
import com.sr.ms.user.userservice.ui.model.LoginRequestModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class UsernameCustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	// private static Logger LOG = Logger.ROOT_LOGGER_NAME
	private UserService userSerive;
	private Environment environment;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported " + request.getMethod());
		}
		try {
			LoginRequestModel loginRequestModel = new ObjectMapper().readValue(request.getInputStream(),
					LoginRequestModel.class);
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					loginRequestModel.getEmail(), loginRequestModel.getPassword(), new ArrayList<>()));
		} catch (Exception e) {
			throw new RuntimeException("Unable marshal Json Object " + e.toString());
		}

	}

	public UsernameCustomAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService,
			Environment environment) {
		super.setAuthenticationManager(authenticationManager);
		this.userSerive = userService;
		this.environment = environment;
	}

	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		String username = ((User) authentication.getPrincipal()).getUsername();
		UserDto userDto = userSerive.getUserDetailsByEmail(username);
		String jwtToken = Jwts.builder().setSubject(userDto.getEmail()).addClaims(null)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() * 10))
				.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret_key")).compact();
		response.addHeader("token", jwtToken);
	}
}

package com.sr.ms.user.userservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sr.ms.user.userservice.service.UserService;
import com.sr.ms.user.userservice.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class UserServiceSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment environment;
	
	@Autowired
	private UserServiceImpl userSerive;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.authorizeRequests().antMatchers("/signup/**").permitAll();
		//.hasIpAddress(environment.getProperty("gateway.ip"))
		http.addFilter(getUsernameCustomAuthenticationFilter());
	}
	
	@Bean 
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	public UsernameCustomAuthenticationFilter getUsernameCustomAuthenticationFilter() throws Exception {
		UsernameCustomAuthenticationFilter authenticationFilter = new UsernameCustomAuthenticationFilter(authenticationManagerBean());
		//authenticationFilter.setAuthenticationManager(authenticationManager());
		return authenticationFilter;
	}
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSerive).passwordEncoder(bCryptPasswordEncoder());
	}
	
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
}

package com.sr.ms.user.userservice.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {
	
	private int id;
	@NotNull(message="firstname is required must not null")
	@Size(min=2,message="minimum 2 characters")
	private String firstname;
	@NotNull(message="lastname is required must not null")
	@Size(min=2,message="minimum 2 characters")
	private String lastename;
	@NotNull(message="password is required,must not null")
	@Size(min=2,max=6,message="password must be minmum character 2 and maxmum character 6")
	private String password;
	@Email
	private String email;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastename() {
		return lastename;
	}
	public void setLastename(String lastename) {
		this.lastename = lastename;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}

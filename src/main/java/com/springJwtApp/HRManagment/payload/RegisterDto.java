package com.springJwtApp.HRManagment.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterDto {

	@NotNull
	@Size(min = 3 , max = 30)
	private String firstname;

	@NotNull
	@Size(min = 3 , max = 30)
	private String lastname;
	
	@Email
	@NotNull
	private String email;
	
	@NotNull
	private String password;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RegisterDto(@NotNull @Size(min = 3, max = 30) String firstname,
			@NotNull @Size(min = 3, max = 30) String lastname, @Email @NotNull String email, @NotNull String password) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
	}
	
	
}

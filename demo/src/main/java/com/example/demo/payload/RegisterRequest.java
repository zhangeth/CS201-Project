package com.example.demo.payload;

import javax.validation.constraints.NotBlank;

public class RegisterRequest {
	@NotBlank
	// @Size(min = 3, max = 20)
	private String username;
	@NotBlank
	// @Size(min = 6, max = 40)
	private String password;

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}

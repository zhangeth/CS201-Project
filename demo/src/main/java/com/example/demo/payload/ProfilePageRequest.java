package com.example.demo.payload;

import javax.validation.constraints.NotBlank;

public class ProfilePageRequest {
	private String username;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}

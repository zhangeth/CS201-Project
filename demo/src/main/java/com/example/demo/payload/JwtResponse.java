package com.example.demo.payload;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long userID;
	private String username;

	public JwtResponse(String accessToken, Long userID, String username) {
		this.token = accessToken;
		this.userID = userID;
		this.username = username;
	}

	public String getAccessToken() {
		return token;
	}

	public String getTokenType() {
		return type;
	}

	public Long getUserID() {
		return userID;
	}

	public String getUsername() {
		return username;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
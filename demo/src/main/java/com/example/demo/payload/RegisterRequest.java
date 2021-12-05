package com.example.demo.payload;

import java.util.Set;
import javax.validation.constraints.*;
public class RegisterRequest {
	@NotBlank
    //@Size(min = 3, max = 20)
    private String username;
    @NotBlank
    //@Size(min = 6, max = 40)
    private String password;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

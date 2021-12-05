package com.example.demo.web;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.Message;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import com.example.demo.payload.LoginRequest;
import com.example.demo.payload.RegisterRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class AuthController {
	@Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> Login(@Valid @RequestBody LoginRequest loginRequest) {
    	if (userRepository.existsByUsername(loginRequest.getUsername())) {
    		return ResponseEntity.ok(new Message("User logged in successfully!"));
    	}
    	else {
    		return ResponseEntity.ok(new Message("User login failed."));
    	}
        
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new Message("Error: Username is already taken!"));
        }
        User user = new User(registerRequest.getUsername(),
                registerRequest.getPassword());
        userRepository.save(user);
        return ResponseEntity.ok(new Message("User registered successfully!"));
    }
}

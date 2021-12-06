package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.domain.UserRepository;
import com.example.demo.security.service.AuthenticationService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class AuthenticationServiceUnitTest {
	AuthenticationService authenticationService;
	
	@Mock UserRepository userRepository;
	@BeforeEach
	void init(@Mock SettingRepository settingRepository) {
	    userService = new DefaultUserService(userRepository, settingRepository, mailClient);
	      
	    Mockito.lenient().when(settingRepository.getUserMinAge()).thenReturn(10);
	        
	    when(settingRepository.getUserNameMinLength()).thenReturn(4);
	        
	    Mockito.lenient()
	        .when(userRepository.isUsernameAlreadyExists(any(String.class)))
	            .thenReturn(false);
	}
}

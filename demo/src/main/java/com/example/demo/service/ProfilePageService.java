package com.example.demo.service;

import java.util.Optional;

import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.ProfilePage;
import com.example.demo.domain.UserRepository;

@Service
public class ProfilePageService {
	 @Autowired
	 UserRepository userRepository;
	 public ProfilePage getProfilePage(String username) {
		 ProfilePage Result = new ProfilePage();
		 Result.username = username;
		 User user = userRepository.findByUsername(username);
		 Result.username = user.getUsername();
		 Result.bio =  ((com.example.demo.domain.User) user).getBio();
		 return Result;
	 }

}
 
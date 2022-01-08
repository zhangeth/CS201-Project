package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.ProfilePage;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;

@Service
public class ProfilePageService {
	@Autowired
	UserRepository userRepository;

	public ProfilePage getProfilePage(String username) {
		ProfilePage Result = new ProfilePage();
		Result.username = username;
		Optional<User> optionalUser = userRepository.findByUsername(username);
		User user = optionalUser.get();
		Result.username = user.getUsername();
		Result.bio = user.getBio();
		return Result;
	}

}

package com.example.demo.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.ProfilePage;
import com.example.demo.payload.ProfilePageRequest;
import com.example.demo.service.ProfilePageService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
public class ProfilePageController {
	 @Autowired
	 ProfilePageService service;
	    
	 @GetMapping("/prof")
	 public ProfilePage getProfilePage(@Valid @RequestBody ProfilePageRequest profilePageRequest)
	 {
	     ProfilePage profPage = service.getProfilePage(profilePageRequest.getUsername());
	        
	     return profPage;
	 }
}

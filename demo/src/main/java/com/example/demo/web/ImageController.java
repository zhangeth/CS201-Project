package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Image;
import com.example.demo.domain.ImageRepository;

@RestController
public class ImageController {
	@Autowired
	private ImageRepository repo;

	@RequestMapping("/images")
	public Iterable<Image> getImages() {
		return repo.findAll();
	}
}

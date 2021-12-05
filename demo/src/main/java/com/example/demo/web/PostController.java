package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Post;
import com.example.demo.domain.PostRepository;

@RestController
public class PostController {
	@Autowired
	private PostRepository repo;

	@RequestMapping("/posts")
	public Iterable<Post> getPosts() {
		return repo.findAll();
	}
}

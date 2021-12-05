package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Group;
import com.example.demo.domain.GroupRepository;

@RestController
public class GroupController {
	@Autowired
	private GroupRepository repo;

	@RequestMapping("/groups")
	public Iterable<Group> getGroups() {
		return repo.findAll();
	}
}

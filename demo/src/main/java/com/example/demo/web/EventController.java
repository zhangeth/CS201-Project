package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Event;
import com.example.demo.domain.EventRepository;

@RestController
public class EventController {
	@Autowired
	private EventRepository repo;

	@RequestMapping("/events")
	public Iterable<Event> getEvents() {
		return repo.findAll();
	}
}

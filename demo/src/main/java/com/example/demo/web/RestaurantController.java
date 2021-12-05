package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Restaurant;
import com.example.demo.domain.RestaurantRepository;

@RestController
public class RestaurantController {
	@Autowired
	private RestaurantRepository repo;

	@RequestMapping("/restaurants")
	public Iterable<Restaurant> getRestaurants() {
		return repo.findAll();
	}
}
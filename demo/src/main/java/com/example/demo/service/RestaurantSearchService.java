package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Restaurant;
import com.example.demo.domain.RestaurantRepository;
import com.example.demo.domain.RestaurantSearchRepository;

@Service
public class RestaurantSearchService {

	@Autowired
	RestaurantSearchRepository SearchRepository;

	@Async
	public CompletableFuture<List<Restaurant>> getAllRestaurants(String search) {
		List<Restaurant> result = SearchRepository.findAllRestaurants(search);
		if (result != null) {
			return CompletableFuture.completedFuture(result);
		} else {
			return CompletableFuture.completedFuture(new ArrayList<Restaurant>());
		}
	}

}
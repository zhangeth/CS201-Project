package com.example.demo.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Restaurant;
import com.example.demo.domain.RestaurantSearchRepository;
import com.example.demo.payload.RestaurantSearchRequest;
import com.example.demo.service.RestaurantSearchService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
public class RestaurantSearchController {
	@Autowired
	RestaurantSearchService service;

	@Autowired
	RestaurantSearchRepository restaurantRepository;

	@GetMapping("/search")
	public List<Restaurant> getAllRestaurants(@Valid @RequestBody RestaurantSearchRequest request)
			throws InterruptedException, ExecutionException {
		String search = request.getSearch();
		String[] names = search.split(",");
		for (String name : names) {
			name.trim();
		}
		Vector<CompletableFuture<List<Restaurant>>> list = new Vector<>();
		int numRestaurants = names.length;

		for (int i = 0; i < numRestaurants; i++) {
			CompletableFuture<List<Restaurant>> future = service.getAllRestaurants(names[i]);
			list.add(future);
		}
		CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()])).join();
		Set<Restaurant> restaurantSet = new HashSet<>();
		for (int i = 0; i < numRestaurants; i++) {
			restaurantSet.addAll(list.get(i).get());
		}
		List<Restaurant> finalList = new ArrayList<>();
		finalList.addAll(restaurantSet);
		return finalList;
	}

}

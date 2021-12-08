package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.domain.Restaurant;
import com.example.demo.domain.RestaurantRepository;
import com.example.demo.domain.RestaurantSearchRepository;
import com.example.demo.payload.RestaurantSearchRequest;


@Service
public class RestaurantSearchService {
	
	@Autowired
	RestaurantSearchRepository SearchRepository;
	@Autowired
	RestaurantRepository RestaurantRepository;
	
	@Async
	public CompletableFuture<Restaurant> getRestaurant(String name){
		Restaurant result = RestaurantRepository.findByName(name);
		if (result != null) {
			return CompletableFuture.completedFuture(result);
		}
		else {
			return CompletableFuture.completedFuture(new Restaurant());
		}
	}
	
	@Async
	public CompletableFuture<List<Restaurant>> getAllRestaurants(String search) {
        List<Restaurant> result = SearchRepository.findAllRestaurants(search);
        if (result !=null) {
        	return CompletableFuture.completedFuture(result);
        }
        else {
        	return CompletableFuture.completedFuture(new ArrayList<Restaurant>());
        }
	}
 
}
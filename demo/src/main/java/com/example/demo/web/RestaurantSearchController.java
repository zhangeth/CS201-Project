package com.example.demo.web;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Restaurant;
import com.example.demo.domain.RestaurantRepository;
import com.example.demo.payload.RestaurantSearchRequest;

import com.example.demo.service.RestaurantSearchService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.jsonwebtoken.lang.Objects;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
public class RestaurantSearchController {
	@Autowired
	RestaurantSearchService service;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@GetMapping("/search")
	public Restaurant getRestaurantYelp(@Valid @RequestBody RestaurantSearchRequest request) throws InterruptedException, ExecutionException{
		String name = request.getName();
		Restaurant r = (Restaurant) restaurantRepository.findByName(name);
		if ( r == null) {
			return service.getRestaurantYelp(name);
		}
		else {
			return r;
		}
	}

}

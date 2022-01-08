package com.example.demo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Message;
import com.example.demo.domain.Restaurant;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import com.example.demo.security.service.UserDetailsImpl;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/api/list")
@CrossOrigin(origins = "http://localhost:3000")
public class ListPageController {
	@Autowired
	private UserRepository UserRepository;

	@GetMapping("/view")
	public ResponseEntity<Object> getList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		if (userDetails == null) {
			return ResponseEntity.badRequest()
					.body(new Message("Error: You must be logged in to view saved Restaurants!"));
		}

		User user = UserRepository.findByUsername(userDetails.getUsername()).get();
		List<JSONObject> restaurantJSONList = new ArrayList<>();

		for (String category : user.getRestaurantMap().keySet()) {
			JSONObject restaurantJSON = new JSONObject();

			restaurantJSON.put("category", category);

			int RestaurantListRandomIndex = new Random().nextInt((user.getRestaurantMap().get(category).size()));
			Restaurant restaurantRandom = (Restaurant) user.getRestaurantMap().get(category)
					.toArray()[RestaurantListRandomIndex];
			int ImageListRandomIndex = new Random().nextInt(restaurantRandom.getImages().size());

			restaurantJSON.put("image", restaurantRandom.getImages().get(ImageListRandomIndex));

			for (Restaurant restaurant : user.getRestaurantMap().get(category)) {
				JSONObject content = new JSONObject();
				restaurantJSON.put("content", content);
				ImageListRandomIndex = new Random().nextInt(restaurant.getImages().size());

				content.put("image", restaurant.getImages().get(ImageListRandomIndex));
				content.put("restaurant", restaurant.getName());
			}

			restaurantJSONList.add(restaurantJSON);
		}

		return new ResponseEntity<>(restaurantJSONList, HttpStatus.OK);
	}
}

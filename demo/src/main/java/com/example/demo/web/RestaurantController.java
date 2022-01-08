package com.example.demo.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Message;
import com.example.demo.domain.Restaurant;
import com.example.demo.domain.RestaurantRepository;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import com.example.demo.security.service.UserDetailsImpl;

@RestController
@RequestMapping("/api/restaurant")
@CrossOrigin(origins = "http://localhost:3000")
public class RestaurantController {
	@Autowired
	private RestaurantRepository RestaurantRepository;
	@Autowired
	private UserRepository UserRepository;

	@PostMapping("/add")
	public ResponseEntity<?> addRestaurant(@AuthenticationPrincipal @NotNull UserDetailsImpl userDetails,
			@RequestParam @NotNull Long restaurantID, @RequestParam @NotBlank String category) {
		if (userDetails == null)
			return ResponseEntity.badRequest().body(new Message("Error: You must be logged in to save restaurants!"));

		User user = UserRepository.findByUsername(userDetails.getUsername()).get();
		Restaurant savedRestaurant;

		// Finds the restaurant of that ID in database if it exists
		if (RestaurantRepository.existsByRestaurantID(restaurantID)) {
			savedRestaurant = RestaurantRepository.findByRestaurantID(restaurantID).get();

			for (String checkCategory : user.getRestaurantMap().keySet()) {
				if (user.getRestaurantMap().get(checkCategory).contains(savedRestaurant))
					return ResponseEntity.badRequest().body(new Message("Error: You already saved this restaurant!"));
			}
		}

		// If restaurant doesn't exist, return error
		else {
			return ResponseEntity.badRequest().body(new Message("Error: That restaurant doesn't exist!"));
		}

		user.addRestaurant(category, savedRestaurant);
		savedRestaurant.addUser(user);
		RestaurantRepository.save(savedRestaurant);
		UserRepository.save(user);

		return ResponseEntity.ok("Saved restaurant successfully!");
	}

	@RequestMapping("/restaurants")
	public Iterable<Restaurant> getRestaurants() {
		return RestaurantRepository.findAll();
	}

	@DeleteMapping("/remove")
	public ResponseEntity<?> removeRestaurant(@AuthenticationPrincipal @NotNull UserDetailsImpl userDetails,
			@RequestParam @NotNull Long restaurantID, @RequestParam @NotBlank String category) {
		if (userDetails == null)
			return ResponseEntity.badRequest().body(new Message("Error: You must be logged in to unsave restaurants!"));

		User user = UserRepository.findByUsername(userDetails.getUsername()).get();
		Restaurant delRestaurant;

		// Finds the restaurant of that ID in database if it exists
		if (RestaurantRepository.existsByRestaurantID(restaurantID)) {
			delRestaurant = RestaurantRepository.findByRestaurantID(restaurantID).get();

			for (String checkCategory : user.getRestaurantMap().keySet()) {
				if (!user.getRestaurantMap().get(checkCategory).contains(delRestaurant))
					return ResponseEntity.badRequest()
							.body(new Message("Error: You haven't yet saved this restaurant!"));
			}
		}

		// If post doesn't exist, return error
		else {
			return ResponseEntity.badRequest().body(new Message("Error: That restaurant doesn't exist!"));
		}

		user.removeRestaurant(category, delRestaurant);
		delRestaurant.removeUser(user);
		RestaurantRepository.save(delRestaurant);
		UserRepository.save(user);

		return ResponseEntity.ok("Saved restaurant successfully!");
	}
}
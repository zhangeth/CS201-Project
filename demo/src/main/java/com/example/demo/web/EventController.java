package com.example.demo.web;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Event;
import com.example.demo.domain.EventRepository;
import com.example.demo.domain.Group;
import com.example.demo.domain.GroupRepository;
import com.example.demo.domain.Message;
import com.example.demo.domain.Restaurant;
import com.example.demo.domain.RestaurantRepository;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import com.example.demo.payload.NewEventRequest;
import com.example.demo.security.service.UserDetailsImpl;

@RestController
@RequestMapping("/api/event")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {
	@Autowired
	private EventRepository EventRepository;
	@Autowired
	private UserRepository UserRepository;
	@Autowired
	private RestaurantRepository RestaurantRepository;
	@Autowired
	private GroupRepository GroupRepository;

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteEvent(@AuthenticationPrincipal @NotNull UserDetailsImpl userDetails,
			@RequestParam @NotNull Long eventID) {
		if (userDetails == null)
			return ResponseEntity.badRequest().body(new Message("Error: You must be logged in to create events!"));

		User user = UserRepository.findByUsername(userDetails.getUsername()).get();

		Event event = EventRepository.findByEventID(eventID).get();
		Restaurant restaurant = event.getRestaurant();
		Group group = event.getEvent_group();

		if (!group.getUsers().contains(user)) {
			return ResponseEntity.badRequest()
					.body(new Message("Error: You cannot delete events from groups of which you aren't a member!"));
		}

		restaurant.removeEvent(event);
		group.removeEvent(event);
		GroupRepository.save(group);
		RestaurantRepository.save(restaurant);
		EventRepository.delete(event);

		return ResponseEntity.ok("Event deleted successfully!");
	}

	@RequestMapping("/events")
	public Iterable<Event> getEvents() {
		return EventRepository.findAll();
	}

	@PostMapping("/create")
	public ResponseEntity<?> newEvent(@AuthenticationPrincipal @NotNull UserDetailsImpl userDetails,
			@Valid @RequestBody NewEventRequest EventRequest) {
		if (userDetails == null)
			return ResponseEntity.badRequest().body(new Message("Error: You must be logged in to create events!"));

		User user = UserRepository.findByUsername(userDetails.getUsername()).get();

		Restaurant restaurant;
		Group group;

		// Finds the group of that ID in database if it exists
		if (GroupRepository.existsByGroupID(EventRequest.getGroupID())) {
			group = GroupRepository.findByGroupID(EventRequest.getGroupID()).get();

			if (!group.getUsers().contains(user)) {
				return ResponseEntity.badRequest()
						.body(new Message("Error: You cannot create events for groups of which you aren't a member!"));
			}
		}

		// Else, fails
		else {
			return ResponseEntity.badRequest().body(new Message("Error: No such group exists!"));
		}

		// Finds the first restaurant of that name in database if it exists
		if (RestaurantRepository.existsByName(EventRequest.getLocation())) {
			restaurant = RestaurantRepository.findByName(EventRequest.getLocation());
		}

		// Else, adds the restaurant to the database
		else {
			restaurant = new Restaurant(EventRequest.getLocation());
			restaurant = RestaurantRepository.save(restaurant);
		}

		// Create new event
		Event event = new Event(EventRequest.getStartTime(), EventRequest.getEndTime(), group, restaurant);

		// Upload post to database
		event = EventRepository.save(event);

		// Updating group and restaurant with new event info
		restaurant.addEvent(event);
		group.addEvent(event);
		GroupRepository.save(group);
		RestaurantRepository.save(restaurant);

		return ResponseEntity.ok("Event created successfully!");
	}
}

package com.example.demo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Follower;
import com.example.demo.domain.FollowerRepository;
import com.example.demo.domain.Message;
import com.example.demo.domain.Post;
import com.example.demo.domain.PostRepository;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import com.example.demo.security.service.UserDetailsImpl;

import net.minidev.json.JSONObject;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/home")
public class HomePageController {
	@Autowired
	private PostRepository PostRepository;
	@Autowired
	private UserRepository UserRepository;
	@Autowired
	private FollowerRepository FollowerRepository;

	@GetMapping("/posts")
	public ResponseEntity<Object> getPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		if (userDetails == null)
			return ResponseEntity.badRequest()
					.body(new Message("Error: You must be logged in to view friend's posts!"));

		User user = UserRepository.findByUsername(userDetails.getUsername()).get();

		List<Follower> friendList = FollowerRepository.findAllByUserFollower(user);
		List<User> friends = new ArrayList<>();

		for (Follower relation : friendList) {
			friends.add(relation.getUserFollowing());
		}

		List<JSONObject> postJSONList = new ArrayList<>();
		final int pageMax = 20;
		int postCount = 0;

		for (Post post : PostRepository.findAllByUserInOrderByDateDesc(friends)) {
			JSONObject postJSON = new JSONObject();

			postJSON.put("username", post.getUser().getUsername());
			postJSON.put("postID", post.getPostID());
			postJSON.put("image", post.getImage());
			postJSON.put("restaurant", post.getRestaurant().getName());
			postJSON.put("likes", post.getLikes());
			postJSON.put("rating", post.getRating());
			postJSON.put("description", post.getPostText());

			postJSONList.add(postJSON);
			postCount++;

			if (postCount >= pageMax)
				break;
		}

		return new ResponseEntity<>(postJSONList, HttpStatus.OK);
	}
}

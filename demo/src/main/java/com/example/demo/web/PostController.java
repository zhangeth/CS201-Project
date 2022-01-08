package com.example.demo.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Comment;
import com.example.demo.domain.CommentRepository;
import com.example.demo.domain.Message;
import com.example.demo.domain.Post;
import com.example.demo.domain.PostRepository;
import com.example.demo.domain.Restaurant;
import com.example.demo.domain.RestaurantRepository;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import com.example.demo.payload.NewCommentRequest;
import com.example.demo.payload.NewPostRequest;
import com.example.demo.security.service.UserDetailsImpl;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/api/post")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {
	@Autowired
	private PostRepository PostRepository;
	@Autowired
	private UserRepository UserRepository;
	@Autowired
	private RestaurantRepository RestaurantRepository;
	@Autowired
	private CommentRepository CommentRepository;

	@PostMapping("/comment/create")
	public ResponseEntity<?> addComment(@AuthenticationPrincipal @NotNull UserDetailsImpl userDetails,
			@Valid @RequestBody NewCommentRequest CommentRequest) {
		if (userDetails == null)
			return ResponseEntity.badRequest().body(new Message("Error: You must be logged in to create comments!"));

		User user = UserRepository.findByUsername(userDetails.getUsername()).get();
		Post post;

		if (PostRepository.existsByPostID(CommentRequest.getPostID()))
			post = PostRepository.findByPostID(CommentRequest.getPostID()).get();
		else
			return ResponseEntity.badRequest().body(new Message("Error: No such post exists!"));

		Comment comment = new Comment(user, post, CommentRequest.getComment());

		comment = CommentRepository.save(comment);

		post.addComment(comment);
		user.addComment(comment);

		UserRepository.save(user);
		PostRepository.save(post);

		return ResponseEntity.ok("Commented successfully!");
	}

	@DeleteMapping("/comment/delete")
	public ResponseEntity<?> deleteComment(@AuthenticationPrincipal @NotNull UserDetailsImpl userDetails,
			@RequestParam @NotNull Long commentID) {
		if (userDetails == null)
			return ResponseEntity.badRequest().body(new Message("Error: You must be logged in to delete comments!"));

		Comment comment;

		if (CommentRepository.existsByCommentID(commentID))
			comment = CommentRepository.findByCommentID(commentID).get();
		else
			return ResponseEntity.badRequest().body(new Message("Error: No such comment exists!"));

		User user = comment.getUser();
		Post post = comment.getPost();

		if (!user.getUserID().equals(userDetails.getUserID())) {
			return ResponseEntity.badRequest().body(new Message("Error: You can only delete your own comment!"));
		}

		// Remove comment from User and Post
		user.removeComment(comment);
		post.removeComment(comment);

		// Update database
		UserRepository.save(user);
		PostRepository.save(post);
		CommentRepository.delete(comment);

		return ResponseEntity.ok("Comment deleted successfully!");
	}

	@DeleteMapping("/remove")
	public ResponseEntity<?> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
			@RequestParam @NotNull Long postID) {
		if (userDetails == null)
			return ResponseEntity.badRequest().body(new Message("Error: You must be logged in to delete posts!"));

		Post post = PostRepository.findByPostID(postID).get();
		User user = post.getUser();

		if (!user.getUserID().equals(userDetails.getUserID())) {
			return ResponseEntity.badRequest().body(new Message("Error: You can only delete your own posts!"));
		}

		Restaurant restaurant = post.getRestaurant();

		// Remove post from User and Restaurant profiles
		user.removePost(post);
		restaurant.removePost(post);
		restaurant.removeImage(post.getImage());

		// Update database
		UserRepository.save(user);
		RestaurantRepository.save(restaurant);
		PostRepository.delete(post);

		return ResponseEntity.ok("Post removed successfully!");
	}

	@GetMapping("/comments")
	public ResponseEntity<Object> getComments(@RequestParam @NotNull Long postID) {
		Post post = PostRepository.findByPostID(postID).get();
		List<JSONObject> commentJSONList = new ArrayList<>();

		for (Comment comment : post.getComments()) {
			JSONObject commentJSON = new JSONObject();

			commentJSON.put("user", comment.getUser().getUserID());
			commentJSON.put("body", comment.getContent());

			commentJSONList.add(commentJSON);
		}

		return new ResponseEntity<>(commentJSONList, HttpStatus.OK);
	}

	@RequestMapping("/posts")
	public Iterable<Post> getPosts() {
		return PostRepository.findAll();
	}

	@PostMapping("/like")
	public ResponseEntity<?> likePost(@AuthenticationPrincipal @NotNull UserDetailsImpl userDetails,
			@RequestParam @NotNull Long postID) {
		if (userDetails == null)
			return ResponseEntity.badRequest().body(new Message("Error: You must be logged in to like posts!"));

		User user = UserRepository.findByUsername(userDetails.getUsername()).get();
		Post likedPost;

		// Finds the post of that ID in database if it exists
		if (PostRepository.existsByPostID(postID)) {
			likedPost = PostRepository.findById(postID).get();

			if (user.getLikedPosts().contains(likedPost))
				return ResponseEntity.badRequest().body(new Message("Error: You already liked this post!"));
		}

		// If post doesn't exist, return error
		else {
			return ResponseEntity.badRequest().body(new Message("Error: That post doesn't exist!"));
		}

		user.addLikedPost(likedPost);
		PostRepository.save(likedPost);
		UserRepository.save(user);

		return ResponseEntity.ok("Liked post successfully!");
	}

	@PostMapping("/create")
	public ResponseEntity<?> newPost(@AuthenticationPrincipal @NotNull UserDetailsImpl userDetails,
			@Valid @RequestBody NewPostRequest PostRequest) {
		if (userDetails == null)
			return ResponseEntity.badRequest().body(new Message("Error: You must be logged in to create posts!"));

		User user = UserRepository.findByUsername(userDetails.getUsername()).get();

		Restaurant restaurant;

		// Finds the first restaurant of that name in database if it exists
		if (RestaurantRepository.existsByName(PostRequest.getLocation())) {
			restaurant = RestaurantRepository.findByName(PostRequest.getLocation());
		}

		// Else, adds the restaurant to the database
		else {
			restaurant = new Restaurant(PostRequest.getLocation());
			restaurant = RestaurantRepository.save(restaurant);
		}

		URL url = null;

		try {
			url = new URL(PostRequest.getImage());
		} catch (MalformedURLException e) {
			return ResponseEntity.badRequest().body(new Message("Error: Image URL is improperly formatted!"));
		}

		// Create new post
		Post post = new Post(url, user, restaurant, PostRequest.getRating(), PostRequest.getPostText(),
				PostRequest.getLocation());

		// Upload post to database
		post = PostRepository.save(post);

		// Updating user and restaurant with new post info
		user.addPost(post);
		restaurant.addPost(post);
		restaurant.addImage(post.getImage());
		UserRepository.save(user);
		RestaurantRepository.save(restaurant);

		return ResponseEntity.ok("Post uploaded successfully!");
	}

	@DeleteMapping("/unlike")
	public ResponseEntity<?> unlikePost(@AuthenticationPrincipal @NotNull UserDetailsImpl userDetails,
			@RequestParam @NotNull Long postID) {
		if (userDetails == null)
			return ResponseEntity.badRequest().body(new Message("Error: You must be logged in to unlike posts!"));

		User user = UserRepository.findByUsername(userDetails.getUsername()).get();
		Post unlikedPost;

		// Finds the post of that ID in database if it exists
		if (PostRepository.existsByPostID(postID)) {
			unlikedPost = PostRepository.findByPostID(postID).get();

			if (!user.getLikedPosts().contains(unlikedPost))
				return ResponseEntity.badRequest().body(new Message("Error: You haven't yet liked this post!"));
		}

		// If post doesn't exist, return error
		else {
			return ResponseEntity.badRequest().body(new Message("Error: That post doesn't exist!"));
		}

		user.removeLikedPost(unlikedPost);
		PostRepository.save(unlikedPost);
		UserRepository.save(user);

		return ResponseEntity.ok("Unliked post successfully!");
	}
}

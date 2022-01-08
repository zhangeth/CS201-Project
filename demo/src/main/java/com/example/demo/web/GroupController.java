package com.example.demo.web;

import java.util.HashSet;
import java.util.Set;

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

import com.example.demo.domain.Group;
import com.example.demo.domain.GroupRepository;
import com.example.demo.domain.Message;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import com.example.demo.security.service.UserDetailsImpl;

@RestController
@RequestMapping("/api/group")
@CrossOrigin(origins = "http://localhost:3000")
public class GroupController {
	@Autowired
	private GroupRepository GroupRepository;
	@Autowired
	private UserRepository UserRepository;

	@PostMapping("/add")
	public ResponseEntity<?> addToGroup(@AuthenticationPrincipal @NotNull UserDetailsImpl userDetails,
			@RequestParam @NotNull Long groupID, @RequestParam @NotBlank Set<String> usernames) {
		if (userDetails == null)
			return ResponseEntity.badRequest()
					.body(new Message("Error: You must be logged in to add members to groups!"));

		User user = UserRepository.findByUsername(userDetails.getUsername()).get();
		Group group = GroupRepository.findByGroupID(groupID).get();

		if (!group.getUsers().contains(user)) {
			return ResponseEntity.badRequest()
					.body(new Message("Error: You cannot add members to groups of which you aren't a member!"));
		}

		Set<User> newMembers = new HashSet<>();

		for (String username : usernames) {
			if (UserRepository.existsByUsername(username))
				newMembers.add(UserRepository.findByUsername(username).get());
		}

		if (newMembers.isEmpty())
			return ResponseEntity.badRequest().body(new Message("Error: No such users exist!"));

		int newCount = 0;

		for (User newMember : newMembers) {
			if (!group.getUsers().contains(newMember)) {
				group.addUser(newMember);
				newMember.addGroup(group);
				UserRepository.save(newMember);

				newCount++;
			}
		}

		if (newCount == 0) {
			return ResponseEntity.badRequest()
					.body(new Message("Error: You can't add members who already are in the group!"));
		}

		GroupRepository.save(group);

		return (newMembers.size() == 1) ? ResponseEntity.ok("New member added successfully!")
				: ResponseEntity.ok("New members added successfully!");
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteGroup(@AuthenticationPrincipal @NotNull UserDetailsImpl userDetails,
			@RequestParam @NotNull Long groupID) {
		if (userDetails == null)
			return ResponseEntity.badRequest().body(new Message("Error: You must be logged in to delete groups!"));

		User user = UserRepository.findByUsername(userDetails.getUsername()).get();
		Group group = GroupRepository.findByGroupID(groupID).get();

		if (!group.getUsers().contains(user)) {
			return ResponseEntity.badRequest()
					.body(new Message("Error: You cannot delete groups of which you aren't a member!"));
		}

		for (User member : group.getUsers()) {
			member.removeGroup(group);
			UserRepository.save(member);
		}

		GroupRepository.delete(group);

		return ResponseEntity.ok("Group deleted successfully!");
	}

	@RequestMapping("/groups")
	public Iterable<Group> getGroups() {
		return GroupRepository.findAll();
	}

	@PostMapping("/create")
	public ResponseEntity<?> newGroup(@AuthenticationPrincipal @NotNull UserDetailsImpl userDetails,
			@RequestParam @NotBlank String groupName, @RequestParam @NotBlank Set<String> usernames) {
		if (userDetails == null)
			return ResponseEntity.badRequest().body(new Message("Error: You must be logged in to create groups!"));

		User admin = UserRepository.findByUsername(userDetails.getUsername()).get();
		Set<User> users = new HashSet<>();

		for (String username : usernames) {
			if (UserRepository.existsByUsername(username) && (!username.equals(admin.getUsername())))
				users.add(UserRepository.findByUsername(username).get());
		}

		if (users.isEmpty())
			return ResponseEntity.badRequest().body(new Message("Error: No such users exist!"));

		users.add(admin);

		System.out.println(users);

		Group group = GroupRepository.save(new Group(users, groupName));

		for (User user : users) {
			user.addGroup(group);
			UserRepository.save(user);
		}

		return ResponseEntity.ok("Group created successfully!");
	}

	@DeleteMapping("/remove")
	public ResponseEntity<?> removeFromGroup(@AuthenticationPrincipal @NotNull UserDetailsImpl userDetails,
			@RequestParam @NotNull Long groupID, @RequestParam @NotBlank Set<String> usernames) {
		if (userDetails == null)
			return ResponseEntity.badRequest()
					.body(new Message("Error: You must be logged in to remove members from groups!"));

		User user = UserRepository.findByUsername(userDetails.getUsername()).get();
		Group group = GroupRepository.findByGroupID(groupID).get();

		if (!group.getUsers().contains(user)) {
			return ResponseEntity.badRequest()
					.body(new Message("Error: You cannot remove members from groups of which you aren't a member!"));
		}

		Set<User> delMembers = new HashSet<>();

		for (String username : usernames) {
			if (UserRepository.existsByUsername(username)) {
				User delMember = UserRepository.findByUsername(username).get();

				if (group.getUsers().contains(delMember)) {
					group.removeUser(delMember);
					delMember.removeGroup(group);
					UserRepository.save(delMember);
				}
			}
		}

		GroupRepository.save(group);

		return (delMembers.size() == 1) ? ResponseEntity.ok("Member removed successfully!")
				: ResponseEntity.ok("Members removed successfully!");
	}
}

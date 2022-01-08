package com.example.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "FollowerTable")
public class Follower {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(nullable = false, updatable = false, unique = true)
	private Long followerID = Long.valueOf(0);

	@ManyToOne
	@JoinColumn(name = "userFollower")
	private User userFollower;

	@ManyToOne
	@JoinColumn(name = "userFollowing")
	private User userFollowing;

	public Follower() {
		super();
	}

	public Follower(User userFollower, User userFollowing) {
		super();
		this.userFollower = userFollower;
		this.userFollowing = userFollowing;
	}

	public Long getFollowerID() {
		return followerID;
	}

	public User getUserFollower() {
		return userFollower;
	}

	public User getUserFollowing() {
		return userFollowing;
	}

	public void setFollowerId(Long followerID) {
		this.followerID = followerID;
	}

	public void setUserFollower(User userFollower) {
		this.userFollower = userFollower;
	}

	public void setUserFollowing(User userFollowing) {
		this.userFollowing = userFollowing;
	}

}
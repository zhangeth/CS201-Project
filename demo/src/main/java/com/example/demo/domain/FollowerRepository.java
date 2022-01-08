package com.example.demo.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
	Boolean existsByUserFollowerAndUserFollowing(User userFollower, User userFollowing);

	List<Follower> findAllByFollowerID(Long followerID);

	List<Follower> findAllByUserFollower(User userFollower);

	Optional<Follower> findByFollowerID(Long followerID);

	Optional<Follower> findByUserFollowerAndUserFollowing(User userFollower, User userFollowing);
}
package com.example.demo.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	Optional<Post> findByPostID(long postID);

//	List<Post> findByUserID(long userID);
	@Query(value = "SELECT * FROM Posts p WHERE p.user.getUserID = ?1", nativeQuery = true)
	List<Post> getUserPosts(long userID);

	List<Post> findByRestaurantID(long restaurantID);

	@Query(value = "COUNT * FROM Posts p where p.restaurantID = ?1", nativeQuery = true)
	long getRestaurantNumPosts(long restaurantID);
}

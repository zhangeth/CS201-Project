package com.example.demo.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	Boolean existsByName(String name);

	Boolean existsByRestaurantID(Long restaurantID);

	List<Restaurant> findAllByCuisine(String cuisine);

	List<Restaurant> findAllByName(String search);

	Restaurant findByName(String name);

	Optional<Restaurant> findByRestaurantID(Long restaurantID);
}

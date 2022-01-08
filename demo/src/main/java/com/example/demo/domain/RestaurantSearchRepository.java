package com.example.demo.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantSearchRepository extends PagingAndSortingRepository<Restaurant, Long> {
	@Query(value = "SELECT * FROM restaurant_table c WHERE " + "c.name LIKE" + " " + ":searchTerm" + "% "
			+ "group by name", nativeQuery = true)
	List<Restaurant> findAllRestaurants(@Param("searchTerm") String searchTerm);
}

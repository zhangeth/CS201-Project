package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "RestaurantTable")
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long restaurantID;

	private String name, cuisine, price, location, menuLink;
	private Double avgRating;
	// private List<Image> images
	// private List<Post> posts
	// private Map<Integer, Post> popularPosts
	// private Map<Integer, Post> criticalPosts
//
//	@Autowired
//	private RestaurantRepository restaurantRepo;

	public Restaurant() {

	}

	public Restaurant(String name, String cuisine, String price, String location) {
		this.name = name;
		this.cuisine = cuisine;
		this.price = price;
		this.location = location;
	}

	// getters/setters
	public void setRestaurantID(long restaurantID) {
		this.restaurantID = restaurantID;
	}

	public long getRestaurantID() {
		return restaurantID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPrice() {
		return price;
	}

	public void setMenuLink(String menuLink) {
		this.menuLink = menuLink;
	}

	public String getMenuLink() {
		return menuLink;
	}

	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}

	public double getAvgRating() {
		return avgRating;
	}
}

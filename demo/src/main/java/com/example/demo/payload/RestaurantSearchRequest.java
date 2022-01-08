package com.example.demo.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RestaurantSearchRequest { // search takes name(s) of restaurants separated by commas if multiple
										// restaurants
	@NotBlank
	@Size(min = 1, max = 200)
	private String search;

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
}

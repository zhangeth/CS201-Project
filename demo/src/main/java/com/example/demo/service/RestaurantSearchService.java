package com.example.demo.service;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.domain.Restaurant;
import com.example.demo.domain.RestaurantRepository;
import com.example.demo.payload.RestaurantSearchRequest;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

@Service
public class RestaurantSearchService {
	
	@Autowired
	RestaurantRepository repository;
	
	public Restaurant getRestaurant(String name) {
        Restaurant result = (Restaurant) repository.findByName(name);
        if (result == null) {
        	
        }
        else {
        	return result;
        }
        return null;
    }
	public Restaurant getRestaurantYelp(String name) 
	{
		try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            String builder = "https://api.yelp.com/v3/businesses/search" + "?term=" + name;
            Request yelpRequest = new Request.Builder().url(builder).method("GET", null).addHeader(
            		"Authorization",
                    "Bearer K39ZV6rt7ZEZSdbaPog5ZtbkdSv1bZEAxDYJGDSqmcAAu924URHKixH7lKBLiHNIgg1ZSnC--05sIy1Oh9HaLiDpaEJR1BRoI3RUfLHsZNT6XVPTnr2P_F2zZgqKYXYx")
                    .build();
            okhttp3.Response response = client.newCall(yelpRequest).execute();

            String json = response.toString();
            System.out.print(json);
            Gson gson = new GsonBuilder().registerTypeAdapter(Restaurant.class, new MyDeserializer()).create();
            Restaurant restaurant = gson.fromJson(Objects.requireNonNull(response.body()).string(), Restaurant.class);
            repository.save(restaurant);
            return restaurant;

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
	}
}
class MyDeserializer implements JsonDeserializer<Restaurant> {
	@Override
	public Restaurant deserialize(JsonElement je, java.lang.reflect.Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonElement content = je.getAsJsonObject().getAsJsonArray("businesses").get(0).getAsJsonObject()
                .get("name");

        return new Gson().fromJson(content, Restaurant.class);
    }

}

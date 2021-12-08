package com.example.demo;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.example.demo.domain.Bookmark;
import com.example.demo.domain.BookmarkRepository;
import com.example.demo.domain.Event;
import com.example.demo.domain.EventRepository;
import com.example.demo.domain.Image;
import com.example.demo.domain.ImageRepository;
import com.example.demo.domain.Post;
import com.example.demo.domain.PostRepository;
import com.example.demo.domain.Restaurant;
import com.example.demo.domain.RestaurantRepository;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	UserRepository UserRepository;
	@Autowired
	ImageRepository ImageRepository;
	@Autowired
	PostRepository PostRepository;
	@Autowired
	RestaurantRepository RestaurantRepository;
	@Autowired
	EventRepository EventRepository;
	@Autowired
	BookmarkRepository BookmarkRepository;

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		logger.info("Hello Spring Boot");
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			// Save demo data to database
			UserRepository.save(new User("username1", "password", "email", "user"));
			UserRepository.save(new User("1", "2", "email", "user"));
			ImageRepository.save(new Image(1, "url"));
			PostRepository.save(new Post());
			//restaurants
			RestaurantRepository.save(new Restaurant("Mcdonalds"));
			RestaurantRepository.save(new Restaurant("a", "AssCuisine", "$$$", "ArseAvenue"));
			RestaurantRepository.save(new Restaurant("b","BootyCuisine", "$$$$", "ButtholeBoulevard"));
			RestaurantRepository.save(new Restaurant("c", "CockCuisine", "$", "CockCourt"));
			
			RestaurantRepository.save(new Restaurant());
			EventRepository.save(new Event());
			BookmarkRepository.save(new Bookmark());
		};
	}
	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(4);
	    executor.setMaxPoolSize(4);
	    executor.setQueueCapacity(500);
	    executor.setThreadNamePrefix("RestaurantLookup-");
	    executor.initialize();
	    return executor;
	}

}

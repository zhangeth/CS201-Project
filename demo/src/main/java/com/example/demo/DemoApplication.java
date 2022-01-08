package com.example.demo;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.example.demo.domain.EventRepository;
import com.example.demo.domain.GroupRepository;
import com.example.demo.domain.PostRepository;
import com.example.demo.domain.Restaurant;
import com.example.demo.domain.RestaurantRepository;
import com.example.demo.domain.UserRepository;

@SpringBootApplication
public class DemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		logger.info("Hello Spring Boot");
	}

	@Autowired
	UserRepository UserRepository;
	@Autowired
	PostRepository PostRepository;
	@Autowired
	RestaurantRepository RestaurantRepository;
	@Autowired
	EventRepository EventRepository;
	@Autowired
	GroupRepository GroupRepository;

	@Bean
	CommandLineRunner runner() {
		return args -> {
			RestaurantRepository.save(new Restaurant("Mcdonalds", "American", "$", "1234 fat food avenue"));
			RestaurantRepository.save(new Restaurant("Wendys", "American", "$", "1234 obese food avenue"));
			RestaurantRepository.save(new Restaurant("Panda Express", "Chinese", "$", "1234 yummy food avenue"));
			RestaurantRepository.save(new Restaurant("Rock & Reilly's USC Village", "Bar", "$$", "3201 S Hoover St"));
			RestaurantRepository.save(new Restaurant("Taco Bell", "Mexican", "$", "3629 S Vermont avenue"));
			RestaurantRepository.save(new Restaurant("Yummys", "Chinese", "$", "1234 yummy food avenue"));
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

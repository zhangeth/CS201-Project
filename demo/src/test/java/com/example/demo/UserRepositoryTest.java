package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;

// Testing CRUD functionality with the UserRepository class in particular
// Testing only focusing on JPA components

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository repository;

	// Testing the addition of a user to the database
	@Test
	public void saveUser() {
		User user = new User();
		entityManager.persistAndFlush(user);
		assertThat(user.getUserID()).isNotNull();
	}

	// Testing the removal of users from the database
	@Test
	public void deleteUsers() {
		entityManager.persistAndFlush(new User());
		entityManager.persistAndFlush(new User());
		repository.deleteAll();
		assertThat(repository.findAll()).isEmpty();
	}
}

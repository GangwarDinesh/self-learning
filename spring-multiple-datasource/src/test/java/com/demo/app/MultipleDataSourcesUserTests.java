package com.demo.app;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.app.model.user.User;
import com.demo.app.repository.user.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class MultipleDataSourcesUserTests {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	void test() {
		User persistedObj = userRepository.save(new User("Dinesh"));
		assertNotNull(userRepository.findById(persistedObj.getId()));
	}

}

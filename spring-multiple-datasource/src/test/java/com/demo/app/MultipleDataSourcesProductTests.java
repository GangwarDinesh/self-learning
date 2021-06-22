package com.demo.app;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.app.model.product.Product;
import com.demo.app.repository.product.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class MultipleDataSourcesProductTests {

	@Autowired
	private ProductRepository productRepository;
	
	@Test
	void test() {
		Product persistedObj = productRepository.save(new Product("Television"));
		assertNotNull(productRepository.findById(persistedObj.getId()));
	}

}

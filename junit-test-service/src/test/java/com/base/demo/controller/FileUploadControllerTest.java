package com.base.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ComponentScan(basePackages = "com.base.demo.controller")
class FileUploadControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@Test
	void test() throws IOException, Exception {
	
        MockMultipartFile mockFile = null;

        try {
            InputStream inputStream = webApplicationContext.getResource("file:C:/Users/Dkumar/Desktop/Sample_CSV.csv").getInputStream();
            mockFile = new MockMultipartFile("file", "Sample_CSV.csv", MediaType.ALL_VALUE, StreamUtils.copyToByteArray(inputStream));
        } catch (IOException e) {
           e.printStackTrace();
        }

	    mvc.perform(MockMvcRequestBuilders.multipart("/upload/csv").file(mockFile))
	      .andExpect(status().isOk());
		
	}
	

}

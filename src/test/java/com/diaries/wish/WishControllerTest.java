package com.diaries.wish;

import com.diaries.wish.controller.WishController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WishController.class) // This annotation will load the specific controller and its dependencies
public class WishControllerTest {

	@Autowired
	private MockMvc mockMvc; // MockMvc will allow you to mock HTTP requests

	@Test
	public void testSayHello() throws Exception {
		// Perform GET request to "/" and verify the response
		mockMvc.perform(get("/")) // Call the endpoint
				.andExpect(status().isOk()) // Expect HTTP 200 status
				.andExpect(content().string("Hello World")); // Expect response body to be "Hello World"
	}
}

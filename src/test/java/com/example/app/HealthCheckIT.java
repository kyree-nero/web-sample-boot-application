package com.example.app;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.app.configuration.WebSecurityConfiguration;

public class HealthCheckIT extends AbstractSecurityWebMvcIT{
	@Autowired MockMvc mockMvc;
	
	//@WithMockUser(value="user", roles=WebSecurityConfiguration.ROLE_MONITORS)
	@Test public void livenessTest() throws Exception{
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/actuator/health", new Object[] {})
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				
		)
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("status", Matchers.equalTo("UP")))
		.andReturn();
	}
	
	//@WithMockUser(value="user", roles=WebSecurityConfiguration.ROLE_MONITORS)
	@Test public void readinessTest() throws Exception{
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/actuator/sample-health", new Object[] {})
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				
		)
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.aMapWithSize(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("$['count']", Matchers.equalTo("2")))
		.andReturn();
	}
	
}

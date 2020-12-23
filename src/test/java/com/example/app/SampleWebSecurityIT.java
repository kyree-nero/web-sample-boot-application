package com.example.app;

import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class SampleWebSecurityIT extends AbstractSecurityWebMvcIT{
	@Autowired JdbcOperations jdbcOperations;
	
	@Test public void testCSRF()throws Exception {
		
		mockMvc
	    .perform(
	    		MockMvcRequestBuilders.post("/")
	    			.with(SecurityMockMvcRequestPostProcessors.csrf()));
	}
	
	@Test public void testClickJacking() throws Exception {
		mockMvc
	    .perform(
	    		MockMvcRequestBuilders.post("/")
	    			.with(SecurityMockMvcRequestPostProcessors.csrf())
	    			
	    			
	    )
	    .andExpect(MockMvcResultMatchers.header()
	    		.string("X-Content-Type-Options", "nosniff"))
	    .andExpect(MockMvcResultMatchers.header()
	    		.string("X-Frame-Options", "DENY"))
	    .andExpect(MockMvcResultMatchers.header()
	    		.string("X-XSS-Protection", "1; mode=block"));
	}
	
	@WithMockUser(value="user", roles="USERS")
	@Test public void securedRequest() throws Exception{
		System.out.println(jdbcOperations.queryForObject("select count(*) FROM AUTH_EXPR" , Integer.class));
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/sample/0", new Object[] {})
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.notNullValue()))
		.andReturn();
	}
	

	@WithMockUser(value="user", roles="USERS")
	@Test public void securedRequestLogout() throws Exception{
		MockHttpSession session = new MockHttpSession();
		Assert.assertFalse(session.isInvalid());
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/logout", new Object[] {})
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.session(session)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
		.andExpect(MockMvcResultMatchers.redirectedUrl("/loggedOut.html"))
		.andReturn();
		Assert.assertTrue(session.isInvalid());
		
	}
	
	
	@WithMockUser(value="userNotInRole", roles="NONAUTHROLE")
	@Test public void unAuthorizedRequest() throws Exception{
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/sample", new Object[] {})
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isForbidden())
		.andReturn();
		
		
		//test spring actuator auditing
		List<AuditEvent> auditEvents = auditingService.find("userNotInRole", startInstant, "AUTHORIZATION_FAILURE");
		Assert.assertNotNull(auditEvents);
		Assert.assertEquals(1, auditEvents.size());
		
		AuditEvent auditEvent = auditEvents.get(0);
		Assert.assertNotNull(auditEvent);
		Assert.assertEquals("userNotInRole", auditEvent.getPrincipal());
		Assert.assertTrue(auditEvent.getTimestamp().isAfter(startInstant));
		Assert.assertEquals("AUTHORIZATION_FAILURE", auditEvent.getType());
		Assert.assertEquals(2, auditEvent.getData().size());
		
		Map<String, Object> data= auditEvent.getData();
		Assert.assertEquals("org.springframework.security.access.AccessDeniedException", data.get("type"));
		Assert.assertEquals("Access is denied", data.get("message"));
	}
	
	
	@Test public void unAuthenticatedRequest() throws Exception{
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/sample", new Object[] {})
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isForbidden())
		.andReturn();
	}
	
	
}

package com.example.app;

import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.app.sample.Sample;
import com.example.app.sample.SampleEntryRepository;
import com.example.app.sample.SampleService;
import com.fasterxml.jackson.databind.ObjectMapper;
@WithMockUser(value="user", roles="USERS")
public class SampleRestControllerIT extends AbstractSecurityWebMvcIT{
	@Autowired MockMvc mockMvc;
	
	@Autowired SampleEntryRepository sampleEntryRepository;

	@Test public void testFindAll() throws Exception {
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/sample", new Object[] {})
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				
		)
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.notNullValue()))
		.andReturn();
	}
	
	
	@Test public void testFindById() throws Exception {
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/sample/0", new Object[] {})
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				
		)
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.notNullValue()))
		.andReturn();
	}
	
	
	
	@Test public void testSave() throws Exception {
		
		long count = sampleEntryRepository.count();
		
		
		Sample requestSample = new Sample();
		requestSample.setContent("some text");
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(requestSample);


		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/sample", new Object[] {})
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonInString)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.notNullValue()))
		.andReturn();
		
		long posttCount = sampleEntryRepository.count();
		
		Assert.assertTrue(posttCount != count);
		
		//test spring actuator auditing
		List<AuditEvent> auditEvents = auditingService.find("user", startInstant, SampleService.AUDIT_TYPE);
		Assert.assertNotNull(auditEvents);
		Assert.assertEquals(1, auditEvents.size());
		
		AuditEvent auditEvent = auditEvents.get(0);
		Assert.assertNotNull(auditEvent);
		Assert.assertEquals("user", auditEvent.getPrincipal());
		Assert.assertTrue(auditEvent.getTimestamp().isAfter(startInstant));
		Assert.assertEquals(SampleService.AUDIT_TYPE, auditEvent.getType());
		Assert.assertEquals(3, auditEvent.getData().size());
		
		Map<String, Object> data= auditEvent.getData();
		Assert.assertEquals("save",  data.get("operation"));
		Assert.assertNotNull(data.get("target"));
		Assert.assertNotNull(data.get("id"));
		Assert.assertNull(data.get("failure"));
		
	}
	

	
	@Test public void testUpdate() throws Exception {
		
		long count = sampleEntryRepository.count();
		
		Sample requestSample = new Sample();
		requestSample.setId(0L);
		requestSample.setContent("update text");
		requestSample.setVersion(0L);
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(requestSample);

		

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.put("/sample/0", new Object[] {})
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonInString)
				.accept(MediaType.APPLICATION_JSON))
				
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.notNullValue()))
		.andReturn();
		
		long posttCount = sampleEntryRepository.count();
		
		Assert.assertTrue(posttCount == count);
		
		//test spring actuator auditing
		List<AuditEvent> auditEvents = auditingService.find("user", startInstant, SampleService.AUDIT_TYPE);
		Assert.assertNotNull(auditEvents);
		Assert.assertEquals(1, auditEvents.size());
		
		AuditEvent auditEvent = auditEvents.get(0);
		Assert.assertNotNull(auditEvent);
		Assert.assertEquals("user", auditEvent.getPrincipal());
		Assert.assertTrue(auditEvent.getTimestamp().isAfter(startInstant));
		Assert.assertEquals(SampleService.AUDIT_TYPE, auditEvent.getType());
		Assert.assertEquals(3, auditEvent.getData().size());
		
		Map<String, Object> data= auditEvent.getData();
		Assert.assertEquals("save",  data.get("operation"));
		Assert.assertNotNull(data.get("target"));
		Assert.assertNotNull(data.get("id"));
		Assert.assertNull(data.get("failure"));
	}
	
	

	@Test public void testUpdateWithValidationException() throws Exception {
		
		long count = sampleEntryRepository.count();
		
		Sample requestSample = new Sample();
		requestSample.setId(0L);
		requestSample.setContent("bad-update");
		requestSample.setVersion(0L);
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(requestSample);

		

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.put("/sample/0", new Object[] {})
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonInString)
				.accept(MediaType.APPLICATION_JSON))
				
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.not(Matchers.empty())))
				.andReturn();
		
		//test spring actuator auditing
				List<AuditEvent> auditEvents = auditingService.find("user", startInstant, SampleService.AUDIT_TYPE);
				Assert.assertNotNull(auditEvents);
				Assert.assertEquals(0, auditEvents.size());
				
	}
	
	@Test 
	public void testUpdateWithBadVersion() throws Exception {
		
		long count = sampleEntryRepository.count();
		
		Sample requestSample = new Sample();
		requestSample.setId(0L);
		requestSample.setContent("update text");
		requestSample.setVersion(-1L);
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(requestSample);

		

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.put("/sample/0", new Object[] {})
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonInString)
				.accept(MediaType.APPLICATION_JSON))
				
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
		
		long posttCount = sampleEntryRepository.count();
		
		Assert.assertTrue(posttCount == count);
		
		//test spring actuator auditing
		List<AuditEvent> auditEvents = auditingService.find("user", startInstant, SampleService.AUDIT_TYPE);
		Assert.assertNotNull(auditEvents);
		Assert.assertEquals(1, auditEvents.size());
		
		AuditEvent auditEvent = auditEvents.get(0);
		Assert.assertNotNull(auditEvent);
		Assert.assertEquals("user", auditEvent.getPrincipal());
		Assert.assertTrue(auditEvent.getTimestamp().isAfter(startInstant));
		Assert.assertEquals(SampleService.AUDIT_TYPE, auditEvent.getType());
		Assert.assertEquals(4, auditEvent.getData().size());
		
		Map<String, Object> data= auditEvent.getData();
		Assert.assertEquals("save",  data.get("operation"));
		Assert.assertNotNull(data.get("target"));
		Assert.assertNotNull(data.get("id"));
		Assert.assertEquals("SampleEntry not found",  data.get("failure"));
	}
	
	
	
	@Test public void testRemove() throws Exception {
		long count = sampleEntryRepository.count();
		
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.delete("/sample/5000", new Object[] {})
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();
		
		long posttCount = sampleEntryRepository.count();
		
		Assert.assertFalse(posttCount == count);
		
		//test spring actuator auditing
		List<AuditEvent> auditEvents = auditingService.find("user", startInstant, SampleService.AUDIT_TYPE);
		Assert.assertNotNull(auditEvents);
		Assert.assertEquals(1, auditEvents.size());
		
		AuditEvent auditEvent = auditEvents.get(0);
		Assert.assertNotNull(auditEvent);
		Assert.assertEquals("user", auditEvent.getPrincipal());
		Assert.assertTrue(auditEvent.getTimestamp().isAfter(startInstant));
		Assert.assertEquals(SampleService.AUDIT_TYPE, auditEvent.getType());
		Assert.assertEquals(2, auditEvent.getData().size());
		
		Map<String, Object> data= auditEvent.getData();
		Assert.assertEquals("remove",  data.get("operation"));
		Assert.assertNotNull(data.get("target"));
		Assert.assertNull(data.get("failure"));
	}
	
	
	
	@Test public void testSaveWithValidationException() throws Exception {
		Sample requestSample = new Sample();
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(requestSample);


		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/sample", new Object[] {})
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonInString)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.not(Matchers.empty())))
		.andReturn();
	}
	
	
}

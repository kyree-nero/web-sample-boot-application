package com.example.app;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.app.sample.SampleDao;


public class SampleJdbcIT extends AbstractWebMvcIT {
	
	@Autowired SampleDao sampleDao;
	
	
	
	
	
	@Test public void test() throws Exception {
		
		Assert.assertNotNull(sampleDao.findSampleCount());
		
	}
	
}

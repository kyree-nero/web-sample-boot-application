package com.example.app.sample;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id="sample-health")
public class SampleMonitorEndpoint {
	@Autowired SampleService sampleService;
	
    @ReadOperation
    public Map<String, String> health() {
    	
        Long count = sampleService.findCountInDb2();
		Map<String, String> target = new HashMap<String, String>();
		target.put("count", count.toString());
		return target;
    }
}

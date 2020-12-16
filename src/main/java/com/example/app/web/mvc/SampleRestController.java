package com.example.app.web.mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.app.domain.Sample;
import com.example.app.services.SampleService;

@RestController
public class SampleRestController implements WebMvcConfigurer {

	@Autowired SampleService sampleService;
	//@Autowired SampleValidator sampleValidator;
	@Autowired SmartValidator sampleValidator;
	
	
	
	@GetMapping(path = "/sample", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Sample> getSample( Model model) {
		return sampleService.findSamples();
	}
	
	@GetMapping(path = "/sample/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Sample getSample(@PathVariable Long id, Model model) {
		return sampleService.findSample(id);
	}
	
	
	@PutMapping(path = "/sample/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Sample update(@Valid @RequestBody Sample sample, @PathVariable Long id, Model model) {
		
		return sampleService.save(sample);
		
	 }

	
	@PostMapping(path = "/sample", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Sample add(@Valid @RequestBody Sample sample, Model model) {
		
		return sampleService.save(sample);
		
	 }
	
	
	@DeleteMapping(path = "/sample/{id}")
	@ResponseBody
	public void remove(@PathVariable Long id, Model model) {
		 sampleService.remove(id);
	}

	@Override
	public Validator getValidator() {
		return sampleValidator;
	}
	 
	
}

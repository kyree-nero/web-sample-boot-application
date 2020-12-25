package com.example.app.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Sample;
import com.example.app.persistence.entities.SampleEntry;
import com.example.app.web.mvc.IncorrectObjectVersionException;

@Service

public class SampleService  {
	public static final String AUDIT_TYPE = "Sample";
	@Autowired com.example.app.persistence.dao.SampleDao dao;
	@Autowired com.example.app.persistence.repositories.SampleEntryRepository sampleEntryRepository;
	@Autowired AuditEventRepository auditEventRepository;
	@Autowired UserService userService;
	
	
	
	public Long findCountInDb() {
		return dao.findSampleCount();
	}

	
	public Long findCountInDb2() {
		return sampleEntryRepository.count();
	}

	
	
	public Sample findSample(Long id) {
		SampleEntry sampleEntry =  sampleEntryRepository.getOne(id);
		Sample sample = new Sample();
		sample.setContent(sampleEntry.getContent());
		sample.setId(sampleEntry.getId());
		sample.setVersion(sampleEntry.getVersion());
		return sample;
		
	}
	
	
	
	public List<Sample> findSamples() {
		
		List<SampleEntry> sampleEntries =  sampleEntryRepository.findAll();
		List<Sample> response = new ArrayList<Sample>();
		for(SampleEntry entry:sampleEntries) {
			Sample sample = new Sample();
			sample.setContent(entry.getContent());
			sample.setId(entry.getId());
			sample.setVersion(entry.getVersion());
			response.add(sample);
		}
		return response;
		
	}


	
	@Transactional
	public Sample save(Sample sample) {
		Map<String, Object> data = new HashMap<String, Object>();
		
		
		data.put("operation", "save");
		data.put("target", sample);
		AuditEvent auditEvent = new AuditEvent(userService.getUsername(), AUDIT_TYPE, data);
		
		try {
			
			SampleEntry sampleEntry = null;
			if(sample.getId() == null) {
				sampleEntry = new SampleEntry();
			}else {
				sampleEntry =  sampleEntryRepository.findByIdAndVersion(sample.getId(), sample.getVersion());
				
				if(sampleEntry == null) {
					data.put("id", sample.getId());
					throw new IncorrectObjectVersionException("SampleEntry not found");
				}
			}
			sampleEntry.setContent(sample.getContent());
			sampleEntry = sampleEntryRepository.save(sampleEntry);
			Sample sampleResponse = new Sample();
			sampleResponse.setId(sampleEntry.getId());
			sampleResponse.setContent(sampleEntry.getContent());
			sampleResponse.setVersion(sampleEntry.getVersion());
			data.put("id", sampleEntry.getId());
			return sampleResponse;
		}catch(Exception e) {
			e.printStackTrace();
			data.put("failure", e.getMessage());
			throw e;
		}finally {
			auditEventRepository.add(auditEvent);
		}
	}

	
	@Transactional
	public void remove(Long id) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("operation", "remove");
		data.put("target", id);
		AuditEvent auditEvent = new AuditEvent(userService.getUsername(), AUDIT_TYPE, data);
		
		try {
			sampleEntryRepository.deleteById(id);
		}catch(Exception e) {
			data.put("failure", e.getMessage());
			throw e;
		}finally {
			auditEventRepository.add(auditEvent);
		}
	}
	
	
	
}

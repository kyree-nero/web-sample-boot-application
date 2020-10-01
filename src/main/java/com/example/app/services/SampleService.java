package com.example.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Sample;
import com.example.app.persistence.entities.SampleEntry;

@Service

public class SampleService  {

	@Autowired com.example.app.persistence.dao.SampleDao dao;
	@Autowired com.example.app.persistence.repositories.SampleEntryRepository sampleEntryRepository;
	
	
	
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
		SampleEntry sampleEntry = null;
		if(sample.getId() == null) {
			sampleEntry = new SampleEntry();
			
		}else {
			sampleEntry =  sampleEntryRepository.findByIdAndVersion(sample.getId(), sample.getVersion());
			if(sampleEntry == null) {
				throw new IllegalArgumentException("SampleEntry not found");
			}
		}
		sampleEntry.setContent(sample.getContent());
		sampleEntry = sampleEntryRepository.save(sampleEntry);
		Sample sampleResponse = new Sample();
		sampleResponse.setId(sampleEntry.getId());
		sampleResponse.setContent(sampleEntry.getContent());
		sampleResponse.setVersion(sampleEntry.getVersion());
		return sampleResponse;
	}

	
	@Transactional
	public void remove(Long id) {
		sampleEntryRepository.deleteById(id);
		
	}
	
	
	
}

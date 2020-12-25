package com.example.app.sample;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleEntryRepository extends JpaRepository<SampleEntry, Long>{
	public SampleEntry findByIdAndVersion(Long id, Long version);
	
}

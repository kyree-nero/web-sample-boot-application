package com.example.app.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.persistence.entities.SampleEntry;

public interface SampleEntryRepository extends JpaRepository<SampleEntry, Long>{
	public SampleEntry findByIdAndVersion(Long id, Long version);
	
}

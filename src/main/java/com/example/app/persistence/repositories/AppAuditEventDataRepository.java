package com.example.app.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.app.persistence.entities.AppAuditEventData;

@Repository
public interface AppAuditEventDataRepository  extends JpaRepository<AppAuditEventData, Long>{

}

package com.example.app.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.app.persistence.entities.AppAuditEvent;

@Repository
public interface AppAuditEventRepository  extends JpaRepository<AppAuditEvent, Long>{
	public List<AppAuditEvent> findByPrincipalAndTypeAndInstantAsNumGreaterThanEqual(String principal, String type, long instant);
}

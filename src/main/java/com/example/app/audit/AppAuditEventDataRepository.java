package com.example.app.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppAuditEventDataRepository  extends JpaRepository<AppAuditEventData, Long>{

}

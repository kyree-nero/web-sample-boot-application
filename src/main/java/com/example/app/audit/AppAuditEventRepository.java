package com.example.app.audit;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppAuditEventRepository  extends JpaRepository<AppAuditEvent, Long>{
	//public List<AppAuditEvent> findByPrincipalAndTypeAndInstantAsNumGreaterThanEqual(String principal, String type, long instant);
	
	// need to use join fetch here to stop audit from being eager
	@Query(""
			+ "SELECT DISTINCT a FROM AppAuditEvent a INNER JOIN FETCH a.data d  "
			+ "WHERE a.principal = :principal AND a.type = :type AND a.instantAsNum >= :instant")
	List<AppAuditEvent> findByPrincipalAndTypeAndInstantAsNumGreaterThanEqual(
			@Param("principal")  String principal, 
			@Param("type")  String type, 
			@Param("instant") long instant); 
}

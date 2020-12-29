package com.example.app.audit;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditingService implements AuditEventRepository{
	
	@Autowired AppAuditEventRepository auditEventRepository;
	@Autowired AppAuditEventDataRepository auditEvenDatatRepository;
	
	@Autowired AppAuditEventConverter appAuditEventConverter;
	@Autowired AuditEventConverter auditEventConverter;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void add(AuditEvent event) {
		AppAuditEvent auditEvent = appAuditEventConverter.convert(event);
		
		List<AppAuditEventData> auditEventData = auditEvent.getData();
		auditEvent.setData(null);
		
		AppAuditEvent savediAuditEvent = auditEventRepository.save(auditEvent);
		
		auditEventData.stream().forEach(
				d -> {
					d.setAppAuditEvent(savediAuditEvent);
					auditEvenDatatRepository.save(d);
		});
		
	}

	@Override
	public List<AuditEvent> find(String principal, Instant after, String type) {
		 List<AppAuditEvent> appAuditEvents = auditEventRepository.findByPrincipalAndTypeAndInstantAsNumGreaterThanEqual(principal, type, after.toEpochMilli());
		 List<AuditEvent> results = appAuditEvents.stream()
				 .map(a -> auditEventConverter.convert(a))
				 .collect(Collectors.toList());
		 return results;
	}
	
	
	
}

package com.example.app;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;

import com.example.app.audit.AuditEventConverter;
import com.example.app.audit.AuditingService;
import com.example.app.persistence.entities.AppAuditEvent;
import com.example.app.persistence.entities.AppAuditEventData;
import com.example.app.persistence.repositories.AppAuditEventRepository;


public class AuditRepositoryIT extends AbstractWebMvcIT {
	@Autowired AuditingService auditingService;
	@Autowired AppAuditEventRepository auditEventRepository;
	@Autowired AuditEventConverter auditEventConverter;
	
	@WithMockUser(value="user", roles="USERS")
	@Test public void test() {
		
		Instant start = Instant.ofEpochMilli(new Date().getTime());
		AppAuditEvent event = new AppAuditEvent();
		event.setInstantAsNum(Instant.now().toEpochMilli());
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String principal = user.getUsername();
		event.setPrincipal(principal);
		event.setType("x");

		event.setData(new ArrayList<AppAuditEventData>());
		AppAuditEventData data = new AppAuditEventData();
		data.setAuditKey("abc");
		data.setAuditValue("123");
		data.setAppAuditEvent(event);
		event.getData().add(data);
		
		
		auditingService.add(auditEventConverter.convert(event));
		
		System.out.println("x");
		
		System.out.println(auditEventRepository.count());
//		
//		List<AppAuditEvent> auditEvents = auditEventRepository.findAll();
//		Assert.assertNotNull(auditEvents);
//		Assert.assertEquals(1, auditEvents.size());
//		System.out.println(auditEvents.get(0));
//		Assert.assertNotNull(auditEvents.get(0));
//		Assert.assertEquals(1, auditEvents.get(0).getData().size());
//		
		List<AuditEvent> auditEvents = auditingService.find("user", start, "x");
		Assert.assertNotNull(auditEvents);
		Assert.assertEquals(1, auditEvents.size());
		Assert.assertNotNull(auditEvents.get(0));
		Assert.assertEquals(1, auditEvents.get(0).getData().size());
	}
}

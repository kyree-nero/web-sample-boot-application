package com.example.app.audit;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuditEventConverter implements Converter<AppAuditEvent, AuditEvent>{

	@Override
	public AuditEvent convert(AppAuditEvent source) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		for(AppAuditEventData sourceData:source.getData()) {
			dataMap.put(sourceData.getAuditKey(), sourceData.getAuditValue());
		}
		
		AuditEvent auditEvent = new AuditEvent(
				Instant.ofEpochMilli(source.getInstantAsNum()),
				source.getPrincipal(), 
				source.getType(), 
				dataMap
		);
		return auditEvent;
		
	}


}

package com.example.app.audit;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AppAuditEventConverter implements Converter<AuditEvent, AppAuditEvent>{

	@Override
	public AppAuditEvent convert(AuditEvent source) {
		AppAuditEvent target = new AppAuditEvent();
		
		target.setInstantAsNum(source.getTimestamp().toEpochMilli());
		target.setPrincipal(source.getPrincipal());
		target.setType(source.getType());
		
		for(String datakey: source.getData().keySet()) {
			AppAuditEventData auditEventData = new AppAuditEventData();
			auditEventData.setAuditKey(datakey);
			String value = source.getData().get(datakey).toString();
			auditEventData.setAuditValue(value);
			target.getData().add(auditEventData);
		}
		return target;
	}

}

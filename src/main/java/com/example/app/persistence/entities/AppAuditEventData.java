package com.example.app.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="AppAuditEventData")
@Table(name = "AUDIT_EVENT_DATA")
public class AppAuditEventData {
	@Id @GeneratedValue
	private long id;
	
	
	@Column(name="AUDIT_KEY")
	private String auditKey;
	
	@Column(name="AUDIT_VALUE")
	private String auditValue;
	
	
	 @ManyToOne(fetch = FetchType.LAZY, optional = false)
	 @JoinColumn(name = "event_id", nullable = false)
	 private AppAuditEvent appAuditEvent;
	 
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getAuditKey() {
		return auditKey;
	}
	public void setAuditKey(String auditKey) {
		this.auditKey = auditKey;
	}
	public String getAuditValue() {
		return auditValue;
	}
	public void setAuditValue(String auditValue) {
		this.auditValue = auditValue;
	}
	
public AppAuditEvent getAppAuditEvent() {
		return appAuditEvent;
	}
	public void setAppAuditEvent(AppAuditEvent appAuditEvent) {
		this.appAuditEvent = appAuditEvent;
	}
	//	public String getAuditEventId() {
//		return auditEventId;
//	}
//	public void setAuditEventId(String auditEventId) {
//		this.auditEventId = auditEventId;
//	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppAuditEventData other = (AppAuditEventData) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}

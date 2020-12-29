package com.example.app.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "AUDIT_EVENT")
public class AppAuditEvent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8454977978024936300L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="TS_NUM")
	private long instantAsNum;
	
	@Column(name="USER")
	private String principal;
	
	@Column(name="TYPE")
	private String type;


	@OneToMany(mappedBy = "appAuditEvent", //fetch = FetchType.EAGER,
    cascade = CascadeType.ALL)
	private List<AppAuditEventData> data = new ArrayList<AppAuditEventData>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public long getInstantAsNum() {
		return instantAsNum;
	}

	public void setInstantAsNum(long instantAsNum) {
		this.instantAsNum = instantAsNum;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<AppAuditEventData> getData() {
		return data;
	}

	public void setData(List<AppAuditEventData> data) {
		this.data = data;
	}

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
		AppAuditEvent other = (AppAuditEvent) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AppAuditEvent [id=" + id + ", instantAsNum=" + instantAsNum + ", principal=" + principal + ", type="
				+ type + ", data=" + data + "]";
	}
	
	
}

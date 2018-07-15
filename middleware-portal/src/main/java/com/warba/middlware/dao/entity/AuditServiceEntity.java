package com.warba.middlware.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Salah Abu Msameh
 */
@Entity
@Table(name = "AUDIT_SERVICES")
public class AuditServiceEntity {

	private long serviceId;
	private String serviceName;
	
	@Id
	@Column(name = "SERVICE_ID")
	public long getServiceId() {
		return serviceId;
	}
	
	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}
	
	@Column(name = "SERVICE_NAME")
	public String getServiceName() {
		return serviceName;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}

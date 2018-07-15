package com.warba.middlware.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Salah Abu Msameh
 */
@Entity
@Table(name = "AUDIT_PAYLOADS")
public class AuditPayload {
	
	private long auditPayloadId;
	private String auditTrxId;
	private long serviceId;
	private int payloadType;
	private String channelKey;
	private Date date;
	//private String payload;
	
	@Id
	@Column(name = "AUDIT_PAYLOAD_ID")
	public long getAuditPayloadId() {
		return auditPayloadId;
	}
	
	public void setAuditPayloadId(long auditPayloadId) {
		this.auditPayloadId = auditPayloadId;
	}
	
	@Column(name = "AUDIT_PAYLOAD_TRX_ID")
	public String getAuditTrxId() {
		return auditTrxId;
	}
	
	public void setAuditTrxId(String auditTrxId) {
		this.auditTrxId = auditTrxId;
	}
	
	@Column(name = "SERVICE_ID")
	public long getServiceId() {
		return serviceId;
	}
	
	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}
	
	@Column(name = "PAYLOAD_TYPE")
	public int getPayloadType() {
		return payloadType;
	}
	
	public void setPayloadType(int payloadType) {
		this.payloadType = payloadType;
	}
	
	@Column(name = "CHANNEL_KEY")
	public String getChannelKey() {
		return channelKey;
	}
	
	public void setChannelKey(String channelKey) {
		this.channelKey = channelKey;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "AUDIT_PAYLOAD_DATE")
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
//	@Column(name = "PAYLOAD")
//	public String getPayload() {
//		return payload;
//	}
//	
//	public void setPayload(String payload) {
//		this.payload = payload;
//	}	
}

package com.soft.middlware.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Salah Abu Msameh
 */
@Entity
@Table(name = "AUDIT_PAYLOADS")
@NamedQueries({
	@NamedQuery(
		name = "get_audits_by_trx_ids", 
		query = "FROM AuditPayload WHERE auditTrxId in (:trxIds) ORDER BY date DESC"
	),
	@NamedQuery(
		name = "get_audits", 
		query = "FROM AuditPayload WHERE serviceId = :serviceId AND date BETWEEN :fromDate AND :toDate ORDER BY date DESC"
	),
	@NamedQuery(
		name = "get_audits_by_payload_type", 
		query = "FROM AuditPayload WHERE serviceId = :serviceId AND payloadType = :payloadType AND date BETWEEN :fromDate AND :toDate ORDER BY date DESC"
	),
	@NamedQuery(
		name = "get_audits_by_channel", 
		query = "FROM AuditPayload WHERE serviceId = :serviceId AND channelKey = :channelKey AND date BETWEEN :fromDate AND :toDate ORDER BY date DESC"
	),
	@NamedQuery(
		name = "get_audits_by_payload_type_and_channel", 
		query = "FROM AuditPayload WHERE serviceId = :serviceId AND payloadType = :payloadType AND channelKey = :channelKey AND date BETWEEN :fromDate AND :toDate ORDER BY date DESC"
	)
})
@NamedNativeQueries({
	@NamedNativeQuery(
		name = "get_audits_by_phrase", 
		query = "SELECT AUDIT_PAYLOAD_ID, AUDIT_PAYLOAD_TRX_ID, SERVICE_ID, PAYLOAD_TYPE, CHANNEL_KEY, AUDIT_PAYLOAD_DATE FROM AUDIT_PAYLOADS WHERE "
				+ "SERVICE_ID = :serviceId AND AUDIT_PAYLOAD_DATE BETWEEN :fromDate AND :toDate AND PAYLOAD LIKE :phrase",
		resultClass = AuditPayload.class
	),
	@NamedNativeQuery(
		name = "get_audits_by_payload_type_phrase", 
		query = "SELECT AUDIT_PAYLOAD_ID, AUDIT_PAYLOAD_TRX_ID, SERVICE_ID, PAYLOAD_TYPE, CHANNEL_KEY, AUDIT_PAYLOAD_DATE FROM AUDIT_PAYLOADS WHERE "
				+ "SERVICE_ID = :serviceId AND PAYLOAD_TYPE = :payloadType AND AUDIT_PAYLOAD_DATE BETWEEN :fromDate AND :toDate AND PAYLOAD LIKE :phrase",
		resultClass = AuditPayload.class
	),
	@NamedNativeQuery(
		name = "get_audits_by_channel_and_phrase", 
		query = "SELECT AUDIT_PAYLOAD_ID, AUDIT_PAYLOAD_TRX_ID, SERVICE_ID, PAYLOAD_TYPE, CHANNEL_KEY, AUDIT_PAYLOAD_DATE FROM AUDIT_PAYLOADS "
				+ "WHERE SERVICE_ID = :serviceId AND CHANNEL_KEY = :channelKey AND AUDIT_PAYLOAD_DATE BETWEEN :fromDate AND :toDate AND PAYLOAD LIKE :phrase",
		resultClass = AuditPayload.class
	),
	@NamedNativeQuery(
		name = "get_audits_by_payload_type_and_channel_and_phrase", 
		query = "SELECT AUDIT_PAYLOAD_ID, AUDIT_PAYLOAD_TRX_ID, SERVICE_ID, PAYLOAD_TYPE, CHANNEL_KEY, AUDIT_PAYLOAD_DATE FROM AUDIT_PAYLOADS "
				+ "WHERE SERVICE_ID = :serviceId AND PAYLOAD_TYPE = :payloadType AND CHANNEL_KEY = :channelKey AND AUDIT_PAYLOAD_DATE BETWEEN :fromDate AND :toDate "
				+ "AND PAYLOAD LIKE :phrase",
		resultClass = AuditPayload.class
	)
})
public class AuditPayload {
	
	private long auditPayloadId;
	private String auditTrxId;
	private long serviceId;
	private int payloadType;
	private String channelKey;
	private Date date;
	
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
}

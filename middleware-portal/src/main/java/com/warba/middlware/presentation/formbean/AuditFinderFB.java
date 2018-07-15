package com.warba.middlware.presentation.formbean;

import java.util.Date;

/**
 * 
 * @author Salah Abu Msameh
 */
public class AuditFinderFB {

	private long serviceId;
	private int payloadType;
	private String channelKey;
	private Date date;
	
	private String auditTrxId;
	private String phrase;
	
	public long getServiceId() {
		return serviceId;
	}
	
	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}
	
	public int getPayloadType() {
		return payloadType;
	}
	
	public void setPayloadType(int payloadType) {
		this.payloadType = payloadType;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getChannelKey() {
		return channelKey;
	}
	
	public void setChannelKey(String channelKey) {
		this.channelKey = channelKey;
	}
	
	public String getAuditTrxId() {
		return auditTrxId;
	}
	
	public void setAuditTrxId(String auditTrxId) {
		this.auditTrxId = auditTrxId;
	}
	
	public String getPhrase() {
		return phrase;
	}
	
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
}

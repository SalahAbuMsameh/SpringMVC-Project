package com.warba.middlware.presentation.formbean;

import java.util.Date;

/**
 * 
 * @author Salah Abu Msameh
 */
public class AuditFinderFB {

	private String serviceId;
	private String operationType;
	private Date date;
	private String channelKey;
	private String phrase;
	
	public String getServiceId() {
		return serviceId;
	}
	
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	public String getOperationType() {
		return operationType;
	}
	
	public void setOperationType(String operationType) {
		this.operationType = operationType;
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
	
	public String getPhrase() {
		return phrase;
	}
	
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
}

package com.warba.middlware.presentation.formbean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @author Salah Abu Msameh
 */
public class AuditFinderFB {

	private long serviceId;
	private int payloadType;
	private String channelKey;
	private String auditTrxIds;
	private String phrase;
	private String dateType;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fromDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date toDate;
	
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
	
	public Date getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	public Date getToDate() {
		return toDate;
	}
	
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	public String getChannelKey() {
		return channelKey;
	}
	
	public void setChannelKey(String channelKey) {
		this.channelKey = channelKey;
	}
	
	public String getDateType() {
		return dateType;
	}
	
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	
	public String getAuditTrxIds() {
		return auditTrxIds;
	}
	
	public void setAuditTrxIds(String auditTrxIds) {
		this.auditTrxIds = auditTrxIds;
	}
	
	public String getPhrase() {
		return phrase;
	}
	
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
}

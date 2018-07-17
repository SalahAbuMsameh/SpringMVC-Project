package com.warba.middlware.service.audit;

/**
 * 
 * @author Salah Abu Msameh
 */
public enum AuditSearchDateType {

	SINGLE("search-date"),
	INTERVAL("search-interval");
	
	private String dateType;
	
	/**
	 * 
	 * @param dateType
	 */
	private AuditSearchDateType(String dateType) {
		this.dateType = dateType;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDateType() {
		return dateType;
	}
}

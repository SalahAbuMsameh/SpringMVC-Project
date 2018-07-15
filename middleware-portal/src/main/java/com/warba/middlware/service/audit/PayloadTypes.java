package com.warba.middlware.service.audit;

/**
 * Payload Types
 * 
 * @author Salah Abu Msameh
 */
public enum PayloadTypes {

	REQUEST(1, "Request"),
	RESPONSE(2, "Response"),
	FAULT(3, "Fault");
	
	private int payloadId;
	private String desc;
	
	/**
	 * 
	 * @param payloadId
	 * @param desc
	 */
	private PayloadTypes(int payloadId, String desc) {
		this.payloadId = payloadId;
		this.desc = desc;
	}
	
	public int getPayloadId() {
		return payloadId;
	}
	
	public String getDesc() {
		return desc;
	}
}

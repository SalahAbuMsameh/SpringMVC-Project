package com.warba.middlware.common;

/**
 * User status
 * @author Salah Abu Msameh
 */
public enum UserStatus {

	ACTIVE(1),
	LOCKED(2),
	INACTIVE(3);
	
	private int statusId;

	private UserStatus(int statusId) {
		this.statusId = statusId;
	}

	public int getStatusId() {
		return statusId;
	}
}

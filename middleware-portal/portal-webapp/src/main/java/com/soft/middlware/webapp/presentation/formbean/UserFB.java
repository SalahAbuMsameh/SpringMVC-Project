package com.soft.middlware.webapp.presentation.formbean;

import java.util.List;

/**
 * User form bean 
 * 
 * @author Salah Abu Msameh
 */
public class UserFB {

	private String username;
	private String displayEnglishName;
	private String displayArabicName;
	private int type;
	private int status;
	private List<String> roles;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getDisplayEnglishName() {
		return displayEnglishName;
	}
	
	public void setDisplayEnglishName(String displayEnglishName) {
		this.displayEnglishName = displayEnglishName;
	}
	
	public String getDisplayArabicName() {
		return displayArabicName;
	}
	
	public void setDisplayArabicName(String displayArabicName) {
		this.displayArabicName = displayArabicName;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public List<String> getRoles() {
		return roles;
	}
	
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}

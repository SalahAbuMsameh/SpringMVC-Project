package com.soft.middlware.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Salah Abu Msameh
 */
@Entity
@Table(name = "MP_USER_PASSWORDS")
public class UserPassword {

	private long passwordId;
	private String password;
	private int status;
	private Date passwordDate;
	
	private User user;
	
	@Id
	@Column(name = "USER_PASS_ID")
	public long getPasswordId() {
		return passwordId;
	}
	
	public void setPasswordId(long passwordId) {
		this.passwordId = passwordId;
	}
	
	@Column(name = "USER_PASS")
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "PASS_STATUS")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "PASS_DATE")
	public Date getPasswordDate() {
		return passwordDate;
	}
	
	public void setPasswordDate(Date passwordDate) {
		this.passwordDate = passwordDate;
	}
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}

package com.warba.middlware.dao.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**
 * Represents user information db entity
 * 
 * @author Salah Abu Msameh
 */
@Entity
@Table(name = "MP_USERS")
public class User {
	
	private long userId;
	private String username;
	private String displayEnglishName;
	private String displayArabicName;
	private int type;
	private int status;
	private Date createDate;
	private Date lastLoginDate;
	
	private List<UserPassword> passwords;
	private List<Role> roles;
	
	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(generator = "userIncrementGenerator")
	@GenericGenerator(name = "userIncrementGenerator", strategy = "increment")
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	@Column(name = "USERNAME")
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "DISPLAY_NAME_EN")
	public String getDisplayEnglishName() {
		return displayEnglishName;
	}

	public void setDisplayEnglishName(String displayEnglishName) {
		this.displayEnglishName = displayEnglishName;
	}
	
	@Column(name = "DISPLAY_NAME_AR")
	public String getDisplayArabicName() {
		return displayArabicName;
	}

	public void setDisplayArabicName(String displayArabicName) {
		this.displayArabicName = displayArabicName;
	}
	
	@Column(name = "USER_TYPE")
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	@Column(name = "STATUS")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Column(name = "LAST_LOGIN_DATE")
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	@Where(clause = "PASS_STATUS = 1")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public List<UserPassword> getPasswords() {
		return passwords;
	}
	
	public void setPasswords(List<UserPassword> passwords) {
		this.passwords = passwords;
	}
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "ROLE_ID"), 
    	inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	public List<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}

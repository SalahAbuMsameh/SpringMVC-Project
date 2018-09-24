package com.soft.middlware.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Salah Abu Msameh
 */
@Entity
@Table(name = "MP_ROLES")
public class Role {

	private long roleId;
	private String role;
	
	@Id
	@Column(name = "ROLE_ID")
	public long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
	@Column(name = "ROLE_VAL")
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
}

package com.soft.middlware.service.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soft.common.Log;
import com.soft.middlware.persistence.DaoException;
import com.soft.middlware.persistence.UserDao;
import com.soft.middlware.persistence.entity.User;

/**
 * This implementation supports system user details specs
 * 
 * @author Salah Abu Msameh
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UserManagementService userMangSrv;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userMangSrv.getUser(username);
		
		if(user == null) {
			return null;
		}
		
		return buildUserDetails(user);
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	private UserDetails buildUserDetails(User user) {
		
		String username = user.getUsername();
		String password = user.getPasswords().get(0).getPassword();
		boolean expired = false;//user.getStatus() == 1;
		boolean locked = user.getStatus() == UserStatus.LOCKED.getStatusId();
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		//get user roles
		userMangSrv.getUserRoles(user.getUserId()).forEach(role -> {
			authorities.add(new GrantedAuthority() {
				
				private static final long serialVersionUID = 1L;
				
				@Override
				public String getAuthority() {
					return role;
				}
			});
		});
		
		return new SystemUserDetails(username, password, expired, locked, authorities, user) ;
	}
	
	/**
	 * 
	 * @author Salah Abu Msameh
	 */
	public class SystemUserDetails implements UserDetails {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String username;
		private String password;
		private boolean expired;
		private boolean locked;
		private List<GrantedAuthority> authorities;
		
		private User userObj;
		
		/**
		 * 
		 * @param username
		 * @param password
		 * @param expired
		 * @param locked
		 * @param authorities
		 */
		public SystemUserDetails(String username, String password, boolean expired, boolean locked, 
				List<GrantedAuthority> authorities, User userObj) {
			
			this.username = username;
			this.password = password;
			this.expired = expired;
			this.locked = locked;
			this.authorities = authorities;
			this.setUserObj(userObj);
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return this.authorities;
		}

		@Override
		public String getPassword() {
			return this.password;
		}

		@Override
		public String getUsername() {
			return this.username;
		}

		@Override
		public boolean isAccountNonExpired() {
			return !this.expired;
		}

		@Override
		public boolean isAccountNonLocked() {
			return !this.locked;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		public User getUserObj() {
			return userObj;
		}

		public void setUserObj(User userObj) {
			this.userObj = userObj;
		}
	}
}

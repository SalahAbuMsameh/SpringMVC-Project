package com.warba.middlware.service.user;

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

import com.warba.common.dao.DaoException;
import com.warba.common.utils.Log;
import com.warba.middlware.dao.UserDao;
import com.warba.middlware.dao.entity.User;

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
	private UserDao userDao;
	
	//loaded users
	private Map<String, UserDetails> users = new HashMap<String, UserDetails>();
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDetails userDetails = users.get(username);
		
		if(userDetails != null) {
			return userDetails;
		}
		
		//load user
		User user = null;
		
		try {
			user = userDao.findByUsername(username); 
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if(user == null) {
			return null;
		}
		
		//build and update cache
		userDetails = buildUserDetails(user);
		users.put(username, userDetails);
		
		return userDetails;
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
		
		try {
			for(String role : userDao.findUserRoles(user.getUserId())) {
				
				authorities.add(new GrantedAuthority() {
					
					private static final long serialVersionUID = 1L;

					@Override
					public String getAuthority() {
						return role;
					}
				});
			}
		} 
		catch (DaoException e) {
			Log.error(UserDetailsService.class, ExceptionUtils.getFullStackTrace(e));
		}
		
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

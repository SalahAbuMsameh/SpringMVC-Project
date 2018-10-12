package com.soft.middlware.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.common.Log;
import com.soft.middlware.persistence.DaoException;
import com.soft.middlware.persistence.UserDao;
import com.soft.middlware.persistence.entity.Role;
import com.soft.middlware.persistence.entity.User;
import com.soft.middlware.service.ServiceException;

/**
 * 
 * @author Salah Abu Msameh
 */
@Service
public class UserManagementService {
	
	@Autowired
	private UserDao userDao;
	
	//users cache
	private Map<String, User> systemUsers = new HashMap<String, User>();
	
	/**
	 * load system users at system startup
	 */
	public void loadSystemUser() {
		userDao.findAll(User.class).forEach(user -> systemUsers.put(user.getUsername(), user));
	}
	
	/**
	 * get all system users
	 * @return
	 */
	public List<User> getSystemUsers() {
		return new ArrayList<>(systemUsers.values());
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public User getUser(String username) {
		
		User user = systemUsers.get(username);
		
		if(user != null) {
			return user;
		}
		
		try {
			user = userDao.findByUsername(username);
		} 
		catch (DaoException e) {
			Log.error(UserManagementService.class, ExceptionUtils.getFullStackTrace(e));
		}
		
		if(user != null) {
			systemUsers.put(username, user);
		}
		
		return user;
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getUserRoles(long userId) {
		
		try {
			return userDao.findUserRoles(userId);
		} 
		catch (DaoException e) {
			Log.error(UserManagementService.class, ExceptionUtils.getFullStackTrace(e));
		}
		
		return new ArrayList<String>();
	}

	/**
	 * 
	 * @param username
	 * @param displayEnglishName
	 * @param displayArabicName
	 * @param type
	 * @param status
	 * @throws ServiceException 
	 */
	public void addSystemUser(String username, String displayEnglishName, String displayArabicName, int type,
			int status, List<String> roles) throws ServiceException {
		
		User user = new User();
		
		user.setUsername(username);
		user.setDisplayEnglishName(displayEnglishName);
		user.setDisplayArabicName(displayArabicName);
		user.setType(type);
		user.setStatus(status);
		user.setCreateDate(new Date());
		
		user.setRoles(roles.stream().map(roleStr -> {
			Role role = new Role();
			role.setRole(roleStr);
			return role;
		}).collect(Collectors.toList()));
		
		try {
			userDao.save(user);
		} 
		catch (DaoException e) {
			Log.error(UserManagementService.class, ExceptionUtils.getFullStackTrace(e));
			throw new ServiceException(e);
		}
	}
}

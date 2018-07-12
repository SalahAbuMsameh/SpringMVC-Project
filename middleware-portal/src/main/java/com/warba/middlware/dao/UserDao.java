package com.warba.middlware.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.warba.middlware.dao.entity.User;
import com.warba.middlware.util.Log;

/**
 * 
 * @author Salah Abu Msameh
 */
@Component
public class UserDao extends DaoBase {
	
	/**
	 * find user for the given username 
	 * 
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		return findWhereField("username", username, User.class);
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> findUserRoles(long userId) {
		
		Session session = openSession();
		String sql = "SELECT R.ROLE_VAL FROM MP_ROLES R\r\n" + 
				"INNER JOIN MP_USER_ROLES UR ON R.ROLE_ID = UR.ROLE_ID\r\n" + 
				"WHERE UR.USER_ID = :userId";
		
		try {
			List<String> roles = session.createNativeQuery(sql, String.class)
					.setParameter("userId", userId)
					.getResultList();
					
			commit(session);
			
			return roles;
			
		} catch (NoResultException ex) {
			rollback(session);
			
		} catch (Exception ex) {
			Log.error(DaoBase.class, null, ex);
			rollback(session);
		}
		
		return null;
	}
}

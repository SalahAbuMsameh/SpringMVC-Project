package com.soft.middlware.persistence;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.soft.middlware.persistence.entity.User;



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
	 * @throws DaoException 
	 */
	public User findByUsername(String username) throws DaoException {
		return findWhere("username", username, User.class);
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws DaoException 
	 */
	public List<String> findUserRoles(long userId) throws DaoException {
		
		Session session = beginTransaction();
		String sql = "SELECT R.ROLE_VAL FROM MP_ROLES R " + 
				"INNER JOIN MP_USER_ROLES UR ON R.ROLE_ID = UR.ROLE_ID " + 
				"WHERE UR.USER_ID = :userId";
		
		try {
			List<?> roles = session.createNativeQuery(sql)
					.setParameter("userId", userId)
					.getResultList();
					
			commit(session);
			return (List<String>) roles;
		} 
		catch (NoResultException ex) {
			rollback(session);
		} 
		catch (Exception ex) {
			rollback(session);
			throw new DaoException(ex);
		}
		
		return null;
	}
}

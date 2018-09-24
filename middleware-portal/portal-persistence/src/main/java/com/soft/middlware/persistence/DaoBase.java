package com.soft.middlware.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.soft.common.Log;

/**
 * Dao base implementation
 * 
 * @author Salah Abu Msameh
 * @since 28/3/2017
 */
public abstract class DaoBase implements DaoModel {
	
	protected static final String PROPERTY_KEY_PASSWORD = "hibernate.connection.password";
	
	/**Entity Manager instance*/
	protected static Map<String, SessionFactory> sessionFactories = new HashMap<String, SessionFactory>();
	
	/**
	 * 
	 * @param dao
	 * @return
	 */
	public SessionFactory getSessionForDAO(DaoModel dao) {
		
		SessionFactory sf;
		String configurationFile = getConfigFileName(dao);
		
		sf = sessionFactories.get(configurationFile);
		
		//initialize session factory
		if(sf == null) {
			
			final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
					.configure(configurationFile)
					.build();
			
			try {
				sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
				sessionFactories.put(configurationFile, sf);

			} catch (Exception e) {
				StandardServiceRegistryBuilder.destroy(registry);
				e.printStackTrace();
			}
		}
		
		return sf;
	}
	
	/**
	 * Determine the session factory related cfg file name
	 * 
	 * @param dao
	 * @return
	 */
	protected String getConfigFileName(DaoModel dao) {
		
		if(dao instanceof AuditDao) {
			return "mp_audit.hibernate.cfg.xml";
		}
		else {
			return "mp.hibernate.cfg.xml";
		}
	}

	/**
	 * Save entity to db
	 * 
	 * @param serviceLimit
	 * @return
	 */
	public boolean save(Object entity) throws DaoException {
		
		Session session = beginTransaction();
		
		try {
			session.persist(entity);
			commit(session);
			
			return true;
			
		} catch (Exception ex) {
			rollback(session);
			Log.error(DaoBase.class, ExceptionUtils.getFullStackTrace(ex));
			throw new DaoException(ex);
		}
	}
	
	/**
	 * Save entity to db
	 * 
	 * @param entity
	 * @return id
	 */
	public int saveEntity(Object entity) throws DaoException {
		
		Session session = beginTransaction();
		int id;
		
		try {
			id=(Integer) session.save(entity);
			commit(session);
			return id;
			
		} catch (Exception ex) {
			rollback(session);
			Log.error(DaoBase.class, ExceptionUtils.getFullStackTrace(ex));
			throw new DaoException(ex);
		}
	}
	
	/**
	 * Update entity in db
	 * 
	 * @param userUsedLimit
	 * @return
	 * @throws DaoException 
	 */
	public boolean update(Object entity) throws DaoException {
		
		Session session = beginTransaction();
		
		try {
			session.update(entity);
			commit(session);
			
			return true;
			
		} catch (Exception ex) {
			rollback(session);
			Log.error(DaoBase.class, ExceptionUtils.getFullStackTrace(ex));
			throw new DaoException(ex);		
		}
	}
	
	/**
	 * Delete entity in db
	 * 
	 * @param userUsedLimit
	 * @return
	 * @throws DaoException 
	 */
	public boolean delete(Object entity) throws DaoException {
		
		Session session = beginTransaction();
		
		try {
			session.delete(entity);
			commit(session);
			
			return true;
			
		} catch (Exception ex) {
			rollback(session);
			Log.error(DaoBase.class, ExceptionUtils.getFullStackTrace(ex));
			throw new DaoException(ex);
		}
	}
	
	/**
	 * find db object by id
	 * 
	 * @param id
	 * @param clazz
	 * @return
	 * @throws DaoException 
	 */
		
	public <T> T findById(String idColumnName, Object id, Class<T> clazz) throws DaoException {
		
		Session session = beginTransaction();
		T object = null;
		
		try {
			object = session.createQuery(QueryBuilder.buildSelectWhereIdQuery(idColumnName, clazz.getSimpleName()), clazz)
					.setParameter(QueryBuilder.COLUMN_ID, id)
					.getSingleResult();
			
			commit(session);
			
		} catch(NoResultException ex) {
			//do nothing
			rollback(session);
			return null;
			
		} catch (Exception ex) {
			rollback(session);
			Log.error(DaoBase.class, ExceptionUtils.getFullStackTrace(ex));
			throw new DaoException(ex);		
		} 
		
		return object;
	}
	
	/**
	 * 
	 */
	@Override
	public <T> T findWhere(String whereColumnName, Object id, Class<T> clazz) throws DaoException {
		return findById(whereColumnName, id, clazz);
	}
	
	/**
	 * Select all the given db class
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> List<T> findAll(Class<T> clazz) {
		
		Session session = beginTransaction();
		List<T> objectsList = null;
		
		try {
			objectsList = session.createQuery(QueryBuilder.buildSelectAllQuery(clazz.getSimpleName()), clazz).getResultList();
			commit(session);
			
		} catch(NoResultException ex) {
			//do nothing
			rollback(session);
			return null;
			
		} catch (Exception ex) {
			rollback(session);
			Log.error(DaoBase.class, "Error while fetching list of " + clazz.getSimpleName(), ex);
			return objectsList;			
		} 
		
		return objectsList;
	}
	
	/**
	 * Begin new transaction in new session
	 * 
	 * @return session object for the created started transaction
	 */
	protected Session beginTransaction() {
		return beginTransaction(null);
	}
	
	
	/**
	 * Begin new transaction in new session
	 * 
	 * @return session object for the created started transaction
	 */
	protected Session beginTransaction(DaoModel dao) {
		Session session = getSessionForDAO(dao).openSession();
		session.beginTransaction();
		return session;
	}
	
	
	/**
	 * Commit transaction and close the related session
	 * 
	 * @param session
	 */
	protected void commit(Session session) {
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Roll back transaction and close the related session
	 * 
	 * @param session
	 */
	protected void rollback(Session session) {
		session.getTransaction().rollback();
		session.close();
	}
	
	/**
	 * Decrypt database password
	 * 
	 * @param configs
	 * @throws Exception 
	 */
//	protected void decryptDatabasePassword(LoadedConfig configs) {
//		
//		Map configsMap = configs.getConfigurationValues();
//		
//		String encryptedPassword = String.valueOf(configsMap.get(PROPERTY_KEY_PASSWORD));
//		String decryptedPassword = "";
//		
//		try {
//			decryptedPassword = AESEncryptor.decrypt(EncryptionKeys.ENCRYPTION_KEY_DB_PASSWORD, encryptedPassword);
//		} catch (Exception e) {
//			throw new RuntimeException("Unable to decrypt database password");
//		}
//		
//		configsMap.put(PROPERTY_KEY_PASSWORD, decryptedPassword);
//	}
}

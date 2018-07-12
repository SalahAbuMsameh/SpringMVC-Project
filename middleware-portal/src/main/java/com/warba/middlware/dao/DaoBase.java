package com.warba.middlware.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Component;

import com.warba.middlware.util.Log;

/**
 * 
 * @author Salah Abu Msameh
 */
@Component
public class DaoBase {

	private static final String DEFAULT_CFG_CONFIG_FILE = "hibernate.cfg.xml";
	
	//session factory
	private SessionFactory sessionFactory;
	
	/**
	 * Initialize the session factory
	 * @throws Exception
	 */
	@PostConstruct
	private void init() throws Exception {
		
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure(DEFAULT_CFG_CONFIG_FILE)
				.build();
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			StandardServiceRegistryBuilder.destroy(registry);
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	/**
	 * find a single entity where the given field
	 * 
	 * @param fieldName
	 * @param fieldValue
	 * @param type
	 * @return
	 */
	public <T> T findWhereField(String fieldName, String fieldValue, Class<T> type) {
		
		Session session = openSession();
		
		try {
			T result = session.createQuery(QueryBuilder.buildQueryWhereField(type.getSimpleName(), fieldName), type)
					.setParameter(fieldName, fieldValue)
					.getSingleResult();
			
			commit(session);
			
			return result;
			
		} catch (NoResultException ex) {
			rollback(session);
		} catch (Exception ex) {
			Log.error(DaoBase.class, null, ex);
			rollback(session);
		}
		
		return null;
	}
	
//	/**
//	 * find list where given field name and value 
//	 * 
//	 * @param fieldName
//	 * @param fieldValue
//	 * @param type
//	 * @return
//	 */
//	public <T> List<T> findAllWhereField(String fieldName, String fieldValue, Class<T> type) {
//		
//		Session session = openSession();
//		
//		try {
//			List<T> results = session.createQuery(QueryBuilder.buildQueryWhereField(type.getSimpleName(), fieldName), type)
//					.setParameter(fieldName, fieldValue)
//					.getResultList();
//			
//			commit(session);
//			
//			return results;
//			
//		} catch (NoResultException ex) {
//			rollback(session);
//		} catch (Exception ex) {
//			Log.error(DaoBase.class, null, ex);
//			rollback(session);
//		}
//		
//		return null;
//	}
	
	/**
	 * find all entity for the given T type 
	 * 
	 * @param type
	 * @return
	 */
	public <T> List<T> findAll(Class<T> type) {
		
		Session session = openSession();
		
		try {
			List<T> reuslt = session.createQuery("from ".concat(type.getSimpleName()), type).getResultList();
			commit(session);
			
			return reuslt;
			
		} catch (NoResultException ex) {
			rollback(session);
		} catch (Exception ex) {
			rollback(session);
		}
		
		return null;
	}
	
	/**
	 * Open new session and begin a transaction
	 * 
	 * @return
	 */
	public Session openSession() {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		return session;
	}
	
	/**
	 * Close current session and commit transaction
	 * 
	 * @param currSession
	 */
	public void commit(Session session) {
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Close current session and commit transaction
	 * 
	 * @param currSession
	 */
	public void rollback(Session session) {
		session.getTransaction().rollback();
		session.close();
	}
}

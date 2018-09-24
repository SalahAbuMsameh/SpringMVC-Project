package com.soft.middlware.persistence;

import java.util.List;

/**
 * Dao behaviors
 * 
 * @author Salah Abu Msameh
 * @since 28/3/2017
 */
public interface DaoModel {
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws DaoException
	 */
	public boolean save(Object entity) throws DaoException;
	
	/**
	 * Update entity in db
	 * 
	 * @param userUsedLimit
	 * @return
	 * @throws DaoException 
	 */
	public boolean update(Object entity) throws DaoException;
	
	/**
	 * find db object by id
	 * 
	 * @param id
	 * @param clazz
	 * @return
	 * @throws DaoException 
	 */
	public <T> T findById(String idColumnName, Object id, Class<T> clazz) throws DaoException;
	
	/**
	 * find db object by id
	 * 
	 * @param id
	 * @param clazz
	 * @return
	 * @throws DaoException 
	 */
	public <T> T findWhere(String whereColumnName, Object id, Class<T> clazz) throws DaoException;
	
	/**
	 * Select all the given db class
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> List<T> findAll(Class<T> clazz);
} 

package com.soft.middlware.service;

import com.soft.middlware.persistence.DaoException;

/**
 * 
 * @author Salah Abu Msameh
 */
public class ServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @param e
	 */
	public ServiceException(DaoException exception) {
		super(exception);
	}
}

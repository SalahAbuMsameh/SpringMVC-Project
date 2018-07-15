package com.warba.middlware.service;

import com.warba.common.dao.DaoException;

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

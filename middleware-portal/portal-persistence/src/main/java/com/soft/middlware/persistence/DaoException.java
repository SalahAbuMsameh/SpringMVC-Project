package com.soft.middlware.persistence;

/**
 * Custom Dao Exception
 * 
 * @author Salah Abu Msameh
 * @since 28/3/2017
 */
public class DaoException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @param cause
	 */
	public DaoException(String cause) {
		super(cause);
	}
	
	/**
	 * 
	 * @param ex
	 */
	public DaoException(Exception ex) {
		super(ex);
	}
}

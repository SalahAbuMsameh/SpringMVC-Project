package com.soft.middlware.persistence;

import org.hibernate.Session;

/**
 * Batch dao is designed to support the inserting huge amount of records (batch insert), same as jdbc executeBatch apis
 * 
 * @author Salah Abu Msameh
 * @since 04/05/2017
 */
public class BatchExecuteDao extends DaoBase {
	
	private static final int BATCH_SIZE = 100;
	
	private Session batchSession;
	private int counter;
	
	/**
	 * Begin new batch transaction
	 */
	public void beginBatch() {
		beginBatch(null);
	}
	
	/**
	 * Begin new batch transaction for the given dao
	 * @param dao
	 */
	public void beginBatch(DaoModel dao) {
		this.batchSession = beginTransaction(dao);
	}
	
	/**
	 * Save the given object within the current batch transaction
	 * 
	 * @param obj
	 */
	public void saveBatch(Object obj) throws DaoException {
		
		if(this.batchSession == null) {
			throw new DaoException("No batch transaction started");
		}
		
		this.batchSession.save(obj);
		flush();
	}
	
	/**
	 * Update the given object within the current batch transaction
	 * 
	 * @param obj
	 */
	public void updateBatch(Object obj) throws DaoException {
		
		if(this.batchSession == null) {
			throw new DaoException("No batch transaction started");
		}
		
		this.batchSession.update(obj);
		flush();
	}
	
	/**
	 * Delete the given object within the current batch transaction
	 * 
	 * @param obj
	 */
	public void deleteBatch(Object obj) throws DaoException {
		
		if(this.batchSession == null) {
			throw new DaoException("No batch transaction started");
		}
		
		this.batchSession.delete(obj);
		flush();
	}
	
	/**
	 * 
	 */
	private void flush() {
		//flush batch
		if(++counter % BATCH_SIZE == 0) {
			this.batchSession.flush();
			this.batchSession.clear();
		}
	}
	
	/**
	 * Commit the current batch transaction
	 * 
	 * @throws DaoException 
	 */
	public void commitBatch() throws DaoException {
		
		if(this.batchSession == null) {
			throw new DaoException("No batch transaction started");
		}
		
		commit(this.batchSession);
		this.batchSession = null;
	}
}

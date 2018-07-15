package com.warba.middlware.dao;

import java.io.Serializable;

import com.warba.common.dao.DaoBase;
import com.warba.common.dao.DaoModel;

/**
 * Middleware portal base dao
 * 
 * @author Salah Abu Msameh
 */
public class MPortalDao extends DaoBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected String getConfigFileName(DaoModel dao) {
		
		if(dao instanceof AuditDao) {
			return "mp_audit.hibernate.cfg.xml";
		}
		else {
			return "mp.hibernate.cfg.xml";
		}
	}
}

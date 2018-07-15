package com.warba.middlware.service.audit;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warba.common.dao.DaoException;
import com.warba.common.utils.Log;
import com.warba.middlware.dao.AuditDao;
import com.warba.middlware.dao.entity.AuditPayload;
import com.warba.middlware.dao.entity.AuditServiceEntity;
import com.warba.middlware.service.ServiceException;

/**
 * Audit ser\vice
 * 
 * @author Salah Abu Msameh
 */
@Service
public class AuditService {

	@Autowired
	private AuditDao auditDao;
	
	//caches
	private List<AuditServiceEntity> services;
	
	/**
	 * Retrieve all the services defined in the audit schema
	 * @return
	 */
	public List<AuditServiceEntity> getAuditServices() {
		
		if(services != null) {
			return services;
		}
		
		services = auditDao.findAll(AuditServiceEntity.class);
		
		return services;
	}
	
	/**
	 * search audit payloads for the given audit transaction id
	 * 
	 * @param auditTrxId
	 * @return
	 */
	public List<AuditPayload> searchPayload(String auditTrxId) {
		return null;
	}
	
	/**
	 * search audit payloads for the given criteria's
	 * 
	 * @param serviceId
	 * @param payloadType
	 * @param channelKey
	 * @param date
	 * @return
	 */
	public List<AuditPayload> searchPayload(long serviceId, int payloadType, String channelKey, Date date) throws ServiceException {
		
		try {
			return auditDao.findAuditPayloads(serviceId, payloadType, channelKey, date);
		} 
		catch (DaoException e) {
			Log.error(AuditService.class, ExceptionUtils.getFullStackTrace(e));
			throw new ServiceException(e);
		}
	}
	
	/**
	 * Retrieve payload for the given audit record id
	 * 
	 * @param auditRecordId
	 * @return
	 * @throws ServiceException 
	 */
	public String getAuditPayload(String auditRecordId) throws ServiceException {
		
		try {
			return auditDao.findPayloadById(auditRecordId);
		} 
		catch (DaoException e) {
			Log.error(AuditService.class, ExceptionUtils.getFullStackTrace(e));
			throw new ServiceException(e);
		}
	}
}

package com.soft.middlware.service.audit;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.common.Log;
import com.soft.middlware.persistence.AuditDao;
import com.soft.middlware.persistence.DaoException;
import com.soft.middlware.persistence.entity.AuditPayload;
import com.soft.middlware.persistence.entity.AuditServiceEntity;
import com.soft.middlware.service.ServiceException;

/**
 * Audit service
 * 
 * @author Salah Abu Msameh
 */
@Service
public class AuditService {

	@Autowired
	private AuditDao auditDao;
	
	//caches
	private Map<Long, String> services;
	
	/**
	 * Retrieve all the services defined in the audit schema
	 * @return
	 */
	public Map<Long, String> getAuditServices() {
		
		if(services != null) {
			return services;
		}
		
		services = new HashMap<Long, String>();
		auditDao.findAll(AuditServiceEntity.class).forEach(entity -> services.put(entity.getServiceId(), entity.getServiceName()));
		
		return services;
	}
	
	/**
	 * Search audit payloads for the given audit transaction id
	 * 
	 * @param trxIds
	 * @return
	 * @throws ServiceException 
	 */
	public List<AuditPayload> searchPayload(List<String> trxIds) throws ServiceException {
		
		try {
			return auditDao.findAuditPayloads(trxIds, 0, 0, null, null, null, null);
		} 
		catch (DaoException e) {
			Log.error(AuditService.class, ExceptionUtils.getFullStackTrace(e));
			throw new ServiceException(e);
		}
	}
	
	/**
	 * Search audit payloads for the given criteria
	 * 
	 * @param trxIds
	 * @param serviceId
	 * @param payloadType
	 * @param channelKey
	 * @param date
	 * @param fromDate
	 * @param toDate
	 * @param phrase
	 * @return
	 * @throws ServiceException
	 */
	public List<AuditPayload> searchPayload(List<String> trxIds, long serviceId, int payloadType, String channelKey,
			Date date, Date fromDate, Date toDate, String phrase) throws ServiceException {
		
		if(date != null) {
			fromDate = date;
			toDate = DateUtils.addDays(date, 1);
		}
		else {
			toDate = DateUtils.addDays(toDate, 1);
		}
		
		try {
			return auditDao.findAuditPayloads(trxIds, serviceId, payloadType, channelKey, fromDate, toDate, phrase);
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

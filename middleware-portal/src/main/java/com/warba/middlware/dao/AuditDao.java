package com.warba.middlware.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import com.warba.common.dao.DaoException;
import com.warba.middlware.dao.entity.AuditPayload;

/**
 * 
 * @author Salah Abu Msameh
 */
@Component
public class AuditDao extends MPortalDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * fetch payloads for the given criteria
	 * 
	 * @param serviceId
	 * @param payloadType
	 * @param channelKey
	 * @param date
	 * @return
	 * @throws DaoException 
	 */
	public List<AuditPayload> findAuditPayloads(long serviceId, int payloadType, String channelKey, Date date) throws DaoException {
		
		List<AuditPayload> payloads = null;
		Session session = beginTransaction();
		
		try {
			Query<AuditPayload> query = session.createQuery(buildQuery(payloadType, channelKey, date), 
					AuditPayload.class);
			query.setParameter("serviceId", serviceId);
			
			if(payloadType > 0) {
				query.setParameter("payloadType", payloadType);
			}
			
			if(!StringUtils.isEmpty(channelKey)) {
				query.setParameter("channelKey", channelKey);
			}
			
			if(date != null) {
				query.setParameter("date", date);
			}
			
			payloads = query.getResultList();
			commit(session);
		} 
		catch (NoResultException ex) {
			rollback(session);
		} 
		catch (Exception ex) {
			rollback(session);
			throw new DaoException(ex);
		}
		
		return payloads;
	}
	
	/**
	 * 
	 * @param auditRecordId
	 * @return
	 * @throws DaoException 
	 */
	public String findPayloadById(String auditRecordId) throws DaoException {
		
		Session session = beginTransaction();
		String payload = null;
		
		try {
			payload = IOUtils.toString(((java.sql.Clob) session.createNativeQuery("SELECT PAYLOAD FROM AUDIT_PAYLOADS WHERE AUDIT_PAYLOAD_ID = :auditRecordId")
					.setParameter("auditRecordId", auditRecordId)
					.getSingleResult()).getCharacterStream());
			
			commit(session);
			return payload;
		} 
		catch (NoResultException ex) {
			rollback(session);
		} 
		catch (Exception ex) {
			rollback(session);
			throw new DaoException(ex);
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param serviceId
	 * @param payloadType
	 * @param channelKey
	 * @param date
	 * @return
	 */
	private String buildQuery(int payloadType, String channelKey, Date date) {
		
		StringBuilder sb = new StringBuilder("FROM AuditPayload WHERE serviceId=:serviceId ");
		
		if(payloadType > 0) {
			sb.append("AND payloadType = :payloadType ");
		}
		
		if(!StringUtils.isEmpty(channelKey)) {
			sb.append("AND channelKey = :channelKey ");
		}
		
		if(date != null) {
			sb.append("AND date = :date ");
		}
		
		return sb.toString();
	}
	
	@Override
	protected Session beginTransaction() {
		return super.beginTransaction(this);
	}
}

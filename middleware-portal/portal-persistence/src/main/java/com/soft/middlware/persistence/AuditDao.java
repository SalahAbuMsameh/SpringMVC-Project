package com.soft.middlware.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.soft.middlware.persistence.entity.AuditPayload;


/**
 * 
 * @author Salah Abu Msameh
 */
@Component
public class AuditDao extends DaoBase {

	/**
	 * fetch audit payloads details for the given criteria
	 * 
	 * @param trxIds
	 * @param serviceId
	 * @param payloadType
	 * @param channelKey
	 * @param fromDate
	 * @param toDate
	 * @param phrase
	 * 
	 * @return
	 * @throws DaoException
	 */
	public List<AuditPayload> findAuditPayloads(List<String> trxIds, long serviceId, int payloadType, String channelKey,
			Date fromDate, Date toDate, String phrase) throws DaoException {
		
		List<AuditPayload> payloads = null;
		Session session = beginTransaction();
		
		try {
			if(!trxIds.isEmpty()) {
				payloads = session.createNamedQuery("get_audits_by_trx_ids", AuditPayload.class)
						.setParameter("trxIds", trxIds)
						.getResultList();
			}
			else if(payloadType > 0 && StringUtils.isEmpty(channelKey)) {
				
				//get by payload type
				if(StringUtils.isEmpty(phrase)) {
					payloads = session.createNamedQuery("get_audits_by_payload_type", AuditPayload.class)
							.setParameter("serviceId", serviceId)
							.setParameter("payloadType", payloadType)
							.setParameter("fromDate", fromDate)
							.setParameter("toDate", toDate)
							.getResultList();
				}
				else {
					//get by payload type & phrase
					payloads = session.getNamedNativeQuery("get_audits_by_payload_type_and_phrase")
							.setParameter("serviceId", serviceId)
							.setParameter("payloadType", payloadType)
							.setParameter("phrase", phrase)
							.setParameter("fromDate", fromDate)
							.setParameter("toDate", toDate)
							.list();
				}
			}
			else if(!StringUtils.isEmpty(channelKey) && payloadType == 0) {
				
				//get by channelKey
				if(StringUtils.isEmpty(phrase)) {
					payloads = session.createNamedQuery("get_audits_by_channel", AuditPayload.class)
							.setParameter("serviceId", serviceId)
							.setParameter("channelKey", channelKey)
							.setParameter("fromDate", fromDate)
							.setParameter("toDate", toDate)
							.getResultList();
				}
				else {
					//get by channelKey & phrase
					payloads = session.getNamedNativeQuery("get_audits_by_channel_and_phrase")
							.setParameter("serviceId", serviceId)
							.setParameter("channelKey", channelKey)
							.setParameter("phrase", "%" + phrase + "%")
							.setParameter("fromDate", fromDate)
							.setParameter("toDate", toDate)
							.list();
				}
			}
			else if(!StringUtils.isEmpty(channelKey) && payloadType > 0) {
				
				//get by payload type and channelKey
				if(StringUtils.isEmpty(phrase)) {
					payloads = session.createNamedQuery("get_audits_by_payload_type_and_channel", AuditPayload.class)
							.setParameter("serviceId", serviceId)
							.setParameter("payloadType", payloadType)
							.setParameter("channelKey", channelKey)
							.setParameter("fromDate", fromDate)
							.setParameter("toDate", toDate)
							.getResultList();
				}
				else {
					//get by channelKey & phrase
					payloads = session.getNamedNativeQuery("get_audits_by_payload_type_and_channel_and_phrase")
							.setParameter("serviceId", serviceId)
							.setParameter("payloadType", payloadType)
							.setParameter("channelKey", channelKey)
							.setParameter("phrase", "%" + phrase + "%")
							.setParameter("fromDate", fromDate)
							.setParameter("toDate", toDate)
							.list();
				}
			}
			else if(StringUtils.isEmpty(channelKey) && payloadType == 0) {
				
				//get by service id only
				if(StringUtils.isEmpty(phrase)) {
					payloads = session.createNamedQuery("get_audits", AuditPayload.class)
							.setParameter("serviceId", serviceId)
							.setParameter("fromDate", fromDate)
							.setParameter("toDate", toDate)
							.getResultList();
				}
				else {
					//get by channelKey & phrase
					payloads = session.getNamedNativeQuery("get_audits_by_phrase")
							.setParameter("serviceId", serviceId)
							.setParameter("phrase", "%" + phrase + "%")
							.setParameter("fromDate", fromDate)
							.setParameter("toDate", toDate)
							.list();
				}
			}
			else {
				rollback(session);
				throw new DaoException("Unknown search criteria");
			}
			
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
	
	@Override
	protected Session beginTransaction() {
		return super.beginTransaction(this);
	}
}

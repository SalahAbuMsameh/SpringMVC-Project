package com.warba.middlware.presentation.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.warba.middlware.presentation.formbean.AuditFinderFB;
import com.warba.middlware.service.audit.AuditSearchDateType;

/**
 * 
 * @author Salah Abu Msameh
 */
@Component
public class AuditFinderFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		
		AuditFinderFB auditFinderFB = null;
		
		try {
			auditFinderFB = (AuditFinderFB) target;
		}
		catch(Exception ex) {
			errors.reject("Not audit form bean");
		}
		
		//if no transaction id(s) provided, than the following become mandatory
		if(!StringUtils.isEmpty(auditFinderFB.getAuditTrxIds())) {
			return;
		}
		
		if(auditFinderFB.getServiceId() <= 0) {
			errors.rejectValue("serviceId", "no.service.selected", "Service is required");
		}
		
		if(AuditSearchDateType.SINGLE.getDateType().equals(auditFinderFB.getDateType())
				&& auditFinderFB.getDate() == null) {
			errors.rejectValue("date", "no.date.selected", "Date is mandatory");
		}
		else if(AuditSearchDateType.INTERVAL.getDateType().equals(auditFinderFB.getDateType())) {
			
			if(auditFinderFB.getFromDate() == null) {
				errors.rejectValue("fromDate", "no.date.selected", "From date is mandatory");
			}
			
			if(auditFinderFB.getToDate() == null) {
				errors.rejectValue("toDate", "no.date.selected", "To date is mandatory");
			}
		}
	}
}

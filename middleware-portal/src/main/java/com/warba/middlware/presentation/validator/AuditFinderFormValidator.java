package com.warba.middlware.presentation.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.warba.middlware.presentation.formbean.AuditFinderFB;

/**
 * 
 * @author Salah Abu Msameh
 */
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
		
		//1. if no transaction id(s) provided, than the following become mandatory
//		if(StringUtils.isEmpty(auditFinderFB.getAuditTrxIds())) {
//			
//		}
		System.out.println("Validating ....");
	}
}

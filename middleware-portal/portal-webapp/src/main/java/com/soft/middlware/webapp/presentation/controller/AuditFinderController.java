package com.soft.middlware.webapp.presentation.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soft.common.Log;
import com.soft.common.util.Utils;
import com.soft.middlware.persistence.entity.AuditPayload;
import com.soft.middlware.service.ServiceException;
import com.soft.middlware.service.audit.AuditService;
import com.soft.middlware.service.audit.PayloadTypes;
import com.soft.middlware.webapp.presentation.Pages;
import com.soft.middlware.webapp.presentation.formbean.AuditFinderFB;
import com.soft.middlware.webapp.presentation.validator.AuditFinderFormValidator;


/**
 * 
 * @author Salah Abu Msameh
 */
@Controller
public class AuditFinderController {
	
	@Autowired
	private AuditService auditSrv;
	
	@Autowired
	private AuditFinderFormValidator afValidator;
	
	/**
	 * prepare audit finder form
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(path = "/audit-finder")
	public String auditFinder(Model model) {
		preparePageData(model, new AuditFinderFB());
		return Pages.AUDIT_FINDER;
	}

	/**
	 * Search for payload audits
	 * 
	 * @param model
	 * @return
	 */
	@PostMapping(path = "/audit-finder")
	public String search(Model model, @Valid AuditFinderFB auditFinderFB, BindingResult bindingResult) {
		
		//validate
		afValidator.validate(auditFinderFB, bindingResult);
		
		if(bindingResult.hasErrors()) {
			preparePageData(model, auditFinderFB);
			return Pages.AUDIT_FINDER;
		}
		
		List<AuditPayload> payloads = null;
		
		try {
			if(!StringUtils.isEmpty(auditFinderFB.getAuditTrxIds())) {
				List<String> ids = Arrays.asList(auditFinderFB.getAuditTrxIds().split("\\s+"));
				payloads = auditSrv.searchPayload(ids);
			}
			else {
				payloads = auditSrv.searchPayload(new ArrayList<>(), auditFinderFB.getServiceId(), auditFinderFB.getPayloadType(), 
						auditFinderFB.getChannelKey(), auditFinderFB.getDate(), auditFinderFB.getFromDate(), auditFinderFB.getToDate(), 
						auditFinderFB.getPhrase());
			}
		}
		catch(ServiceException ex) {
			bindingResult.reject("global", "Error, " + ex.getMessage());
		}
		
		preparePageData(model, auditFinderFB);
		model.addAttribute("payloads", payloads);
		
		return Pages.AUDIT_FINDER;
	}
	
	/**
	 * get payload content for the given audit payload id
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@GetMapping(path = "/audit-finder/getPayload/{id}")
	public String getPayload(@PathVariable String id) {
		
		if(StringUtils.isEmpty(id)) {
			return "Error, audit record id is mandatory";
		}
		
		String payload = null;
		
		try {
			payload = auditSrv.getAuditPayload(id);
		} 
		catch (ServiceException e) {
			Log.error(AuditFinderController.class, ExceptionUtils.getFullStackTrace(e));
			return payload;
		}
		
		try {
			return Utils.prettyXMLFormat("<envelope>" + payload + "</envelope>");
		}
		catch (TransformerException e) {
			Log.error(AuditFinderController.class, "Unable to pretty format xml, " + e.getException());
		}
		
		return payload;
	}
	
	/**
	 * 
	 * @param model
	 * @param auditFinderFB
	 */
	private void preparePageData(Model model, AuditFinderFB auditFinderFB) {
		
		model.addAttribute("services", auditSrv.getAuditServices());
		model.addAttribute("payloadTypes", PayloadTypes.values());
		model.addAttribute("auditFinderFB", auditFinderFB);
	}
}

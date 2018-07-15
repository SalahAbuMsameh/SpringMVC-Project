package com.warba.middlware.presentation.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.warba.common.utils.Log;
import com.warba.middlware.dao.entity.AuditPayload;
import com.warba.middlware.presentation.Pages;
import com.warba.middlware.presentation.formbean.AuditFinderFB;
import com.warba.middlware.service.ServiceException;
import com.warba.middlware.service.audit.AuditService;
import com.warba.middlware.service.audit.PayloadTypes;

/**
 * 
 * @author Salah Abu Msameh
 */
@Controller
public class AuditFinderController {
	
	@Autowired
	private AuditService auditSrv;
	
	/**
	 * prepare audit finder form
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(path = "/audit-finder")
	public String auditFinder(Model model) {
		
		preparePageData(model);
		model.addAttribute("auditFinderFB", new AuditFinderFB());
		
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
		
		List<AuditPayload> payloads = null;
		
		try {
			if(!StringUtils.isEmpty(auditFinderFB.getAuditTrxId())) {
				payloads = auditSrv.searchPayload(auditFinderFB.getAuditTrxId());
			}
			else {
				payloads = auditSrv.searchPayload(auditFinderFB.getServiceId(), auditFinderFB.getPayloadType(), auditFinderFB.getChannelKey(), auditFinderFB.getDate());
			}
		}
		catch(ServiceException ex) {
			bindingResult.reject("global", "Error, " + ex.getMessage());
		}
		
		preparePageData(model);
		model.addAttribute("auditFinderFB", auditFinderFB);
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
		
		try {
			return auditSrv.getAuditPayload(id);
		} 
		catch (ServiceException e) {
			Log.error(AuditFinderController.class, ExceptionUtils.getFullStackTrace(e));
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param model
	 */
	private void preparePageData(Model model) {
		model.addAttribute("services", auditSrv.getAuditServices());
		model.addAttribute("payloadTypes", PayloadTypes.values());
	}
}

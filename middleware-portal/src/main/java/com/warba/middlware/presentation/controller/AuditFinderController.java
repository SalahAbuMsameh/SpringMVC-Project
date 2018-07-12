package com.warba.middlware.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.warba.middlware.presentation.Pages;
import com.warba.middlware.presentation.formbean.AuditFinderFB;

/**
 * 
 * @author Salah Abu Msameh
 */
@Controller
public class AuditFinderController {

	@GetMapping(path = "/audit-finder")
	public String auditFinder(Model model) {
		
		model.addAttribute("auditFinderFB", new AuditFinderFB());
		
		return Pages.AUDIT_FINDER;
	}
}

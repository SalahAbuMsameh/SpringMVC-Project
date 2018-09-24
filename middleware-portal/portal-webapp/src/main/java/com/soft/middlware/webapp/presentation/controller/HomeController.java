package com.soft.middlware.webapp.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.soft.middlware.webapp.presentation.Pages;


/**
 * 
 * @author Salah Abu Msameh
 */
@Controller
public class HomeController {
	
	/**
	 * 
	 * @return
	 */
	@GetMapping(path = "/")
	public String defaultUrl() {
		return Pages.REDIRECT_HOME_PAGE;
	}
	
	/**
	 * 
	 * @return
	 */
	@GetMapping(path = "/home")
	public String home() {
		return Pages.HOME_PAGE;
	}
}

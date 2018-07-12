package com.warba.middlware.presentation.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.warba.middlware.presentation.Pages;
import com.warba.middlware.presentation.formbean.LoginFB;

/**
 * 
 * @author Salah Abu Msameh
 */
@Controller
public class LoginController {
	
	/**
	 * Prepare login form bean
	 * @return
	 */
	@GetMapping("/login")
	public String login(Model model) {
		
		//check if user logged in
		if(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
			return Pages.HOME_PAGE;
		}
		
		//prepared login form
		model.addAttribute("loginFB", new LoginFB());
		
		return Pages.LOGIN_PAGE;
	}
	
	/**
	 * Prepare login form bean
	 * @return
	 */
	@GetMapping("/logout")
	public String logout(Model model) {
		model.addAttribute("loginFB", new LoginFB());
		return Pages.LOGIN_PAGE;
	}
}

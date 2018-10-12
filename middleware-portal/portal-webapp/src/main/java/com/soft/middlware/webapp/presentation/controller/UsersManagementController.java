package com.soft.middlware.webapp.presentation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soft.middlware.service.ServiceException;
import com.soft.middlware.service.user.UserManagementService;
import com.soft.middlware.webapp.presentation.Pages;
import com.soft.middlware.webapp.presentation.formbean.UserFB;

/**
 * 
 * @author Salah Abu Msameh
 */
@Controller("/users")
public class UsersManagementController {

	@Autowired
	private UserManagementService userMangSrv;
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(path = "/")
	public String usersHomePage(Model model) {
		
		model.addAttribute("users", userMangSrv.getSystemUsers());
		model.addAttribute("userFB", new UserFB());
		
		return Pages.USERS_MANG_HOME;
	}
	
	/**
	 * 
	 * @return
	 */
	@PostMapping(path = "/addUser")
	@ResponseBody
	public String addSystemUser(@Valid UserFB userFB, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "error" + bindingResult.getAllErrors().get(0).getDefaultMessage();
		}
		
		try {
			userMangSrv.addSystemUser(userFB.getUsername(), userFB.getDisplayEnglishName(), userFB.getDisplayArabicName()
					, userFB.getType(), userFB.getStatus(), userFB.getRoles());
		} catch (ServiceException e) {
			return new StringBuilder("{\"error\": \"").append(e.getMessage()).append("\"}").toString();
		}
		
		return "User added successfully";
	}
}

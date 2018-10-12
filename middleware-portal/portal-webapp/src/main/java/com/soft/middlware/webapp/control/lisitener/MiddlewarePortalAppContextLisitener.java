package com.soft.middlware.webapp.control.lisitener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.soft.middlware.service.user.UserManagementService;

/**
 * 
 * @author Salah Abu Msameh
 */
@Component
public class MiddlewarePortalAppContextLisitener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserManagementService userMangSrv;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//load system users
		userMangSrv.loadSystemUser();
	}
}

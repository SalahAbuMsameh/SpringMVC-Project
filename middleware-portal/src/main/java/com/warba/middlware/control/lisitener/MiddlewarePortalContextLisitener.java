package com.warba.middlware.control.lisitener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.warba.common.utils.Log;

/**
 * 
 * @author Salah Abu Msameh
 */
@WebListener
public class MiddlewarePortalContextLisitener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		Log.init("D:/logs/MiddlewarePortal/MiddlewarePortal.log");
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {}
}

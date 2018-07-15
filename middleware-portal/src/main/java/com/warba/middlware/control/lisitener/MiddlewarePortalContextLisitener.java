package com.warba.middlware.control.lisitener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.warba.common.dao.DaoException;
import com.warba.common.dao.config.ConfigEntity;
import com.warba.common.utils.Log;
import com.warba.middlware.dao.MPortalDao;

/**
 * 
 * @author Salah Abu Msameh
 */
@WebListener
public class MiddlewarePortalContextLisitener implements ServletContextListener {

	private static final int KEY_MIDDLEWARE_PORTAL_LOG_FILE = 20;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		//init log
		String dbLogFilePath = "./logs/MiddlewarePortal.log";//init with default value
		ConfigEntity configEntity = null;
		
		try {
			configEntity = new MPortalDao().findConfigById(KEY_MIDDLEWARE_PORTAL_LOG_FILE);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		if(configEntity != null) {
			dbLogFilePath = configEntity.getConfigValue();
		}
		
		Log.init(dbLogFilePath);	
		//Log.init("D:/logs/MiddlewarePortal/MiddlewarePortal.log");
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {}
}

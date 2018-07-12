package com.warba.middlware.util;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * 
 * @author Salah Abu Msameh
 */
public class Log {

	static {
		PropertyConfigurator.configure(Log.class.getClassLoader().getResourceAsStream("log4j.properties"));
	}
	
	/**
	 * Log info message
	 * 
	 * @param source
	 * @param message
	 */
	public static void info(Class<?> type, String message) {
		info(type, message, null);
	}
	
	/**
	 * Log info message
	 * 
	 * @param source
	 * @param message
	 */
	public static void info(Class<?> type, String message, Throwable cause) {
		logMessage(type, message, cause, Level.INFO);
	}
	
	/**
	 * Log debug message
	 * 
	 * @param source
	 * @param message
	 */
	public static void debug(Class<?> type, String message) {
		debug(type, message, null);
	}
	
	/**
	 * Log debug message
	 * 
	 * @param source
	 * @param message
	 */
	public static void debug(Class<?> type, String message, Throwable cause) {
		logMessage(type, message, cause, Level.DEBUG);
	}
	
	/**
	 * Log debug message
	 * 
	 * @param source
	 * @param message
	 */
	public static void error(Class<?> type, String message) {
		debug(type, message, null);
	}
	
	/**
	 * Log debug message
	 * 
	 * @param source
	 * @param message
	 */
	public static void error(Class<?> type, String message, Throwable cause) {
		logMessage(type, message, cause, Level.ERROR);
	}
	
	/**
	 * Log the actual message
	 * 
	 * @param source
	 * @param message
	 */
	private static void logMessage(Class<?> type, String message, Throwable cause, Level level) {
		
		Logger logger = LogManager.getLogger(type);
		
		if(cause == null) {
			logger.log(level, message);
		} 
		else {
			logger.log(level, message, cause);
		}
	}
}

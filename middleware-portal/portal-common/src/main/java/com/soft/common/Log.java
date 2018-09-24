package com.soft.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Middleware services central log
 * 
 * @author Salah Abu Msameh
 * @since 9/4/2017
 */
public class Log {
	
	public static final String LOG4J_CONFIG_FILE_NAME = "log4j.properties";
	
	/**
	 * Initialize log for the given path
	 * 
	 * @param logFilePath
	 */
	public static void init(String logFilePath) {
		init(logFilePath, null);
	}
	
	/**
	 * Initialize log for the given path
	 * 
	 * @param logFilePath
	 */
	public static void init(String logFilePath, String initializer) {
        
		Properties props = new Properties();
		InputStream configStream = null;
		
		//load log4j config file
	    try { 
	        configStream = Log.class.getClassLoader().getResourceAsStream(Log.LOG4J_CONFIG_FILE_NAME); 
	        props.load(configStream); 
	    } 
	    catch (IOException e) { 
	        System.out.println("Cannot laod log4j configuration file"); 
	    } 
	    finally {
	    	if(configStream != null) {
	    		try {
					configStream.close();
				} catch (IOException e) {}
	    	}
	    }
	    
	    props.setProperty("log4j.appender.FILE.File", logFilePath); 
	    BasicConfigurator.resetConfiguration();
	    PropertyConfigurator.configure(props);
	    
	    //log init message
	    StringBuilder sb = new StringBuilder("Log has been initialized successfully, log path [")
		.append(logFilePath)
		.append("]");
	    
	    if(!StringUtils.isEmpty(initializer)) {
	    	sb.append(", initializer [").append(initializer).append("]");
	    }
	    
	    info(Log.class, sb.toString());
	}
	
    /**
     *
     * @param source
     * @param msg
     * @deprecated Use the method that accepts .class argument 
     */
    public static void debug(Object source, String msg) {
    	debug(source.getClass(), msg, null);
    }

    /**
     * 
     * @param source
     * @param msg
     * @param cause
     * @deprecated Use the method that accepts .class argument 
     */
    public static void debug(Object source, String msg, Throwable cause) {
        debug(source.getClass(), msg, cause);
    }
    
    /**
     * 
     * @param source
     * @param msg
     * @param cause
     */
    public static void debug(Class<?> clazz, String msg) {
    	debug(clazz, msg, null);
    }
    
    /**
     * 
     * @param source
     * @param msg
     * @param cause
     */
    public static void debug(Class<?> clazz, String msg, Throwable cause) {
        
    	Logger logger = Logger.getLogger(clazz);
    	
    	if(cause != null) {
    		logger.debug(msg, cause);
    	} else {
    		logger.debug(msg);
    	}
    }
    
    
    /**
     *
     * @param source
     * @param msg
     * @deprecated Use the method that accepts .class argument 
     */
    public static void info(Object source, String msg) {        
        info(source.getClass(), msg, null);
    }
    
    /**
     *
     * @param source
     * @param msg
     * @param cause
     * @deprecated Use the method that accepts .class argument 
     */
    public static void info(Object source, String msg, Throwable cause) {
    	info(source.getClass(), msg, cause);
    }
    
    /**
    *
    * @param source
    * @param msg
    */
   public static void info(Class<?> clazz, String msg) {        
	   info(clazz, msg, null);
   }
    
    /**
    *
    * @param source
    * @param msg
    * @param cause
    */
   public static void info(Class<?> clazz, String msg, Throwable cause) {
       
	   Logger logger = Logger.getLogger(clazz);
       
       if(cause == null) {
    	   logger.info(msg);
       } else {
    	   logger.info(msg, cause);
       }
   }

    /**
     *
     * @param source
     * @param msg
     */
    public static void error(Class<?> clazz, String msg) {
        error(clazz, msg, null);
    }
    
    /**
    *
    * @param source
    * @param msg
    * @deprecated Use the method that accepts .class argument 
    */
   public static void error(Object source, String msg) {
       error(source.getClass(), msg, null);
   }

    /**
     *
     * @param source
     * @param msg
     * @param cause
     * @deprecated Use the method that accepts .class argument 
     */
    public static void error(Object source, String msg, Throwable cause) {
        error(source.getClass(), msg, cause);
    }
    
    /**
    * 
    * @param source
    * @param msg
    * @param cause
    */
   public static void error(Class<?> clazz, String msg, Throwable cause) {
       
	   Logger logger = Logger.getLogger(clazz);
       
	   if(cause != null) {
    	   logger.error(msg, cause);
       } else {
    	   logger.error(msg);
       }
   }

    /**
     *
     * @param source
     * @param msg
     */
    public static void warn(Object source, String msg) {
        Logger logger = Logger.getLogger(source.getClass());
        logger.warn(msg);
    }

    /**
     *
     * @param source
     * @param msg
     * @param cause
     */
    public static void warn(Object source, String msg, Throwable cause) {
        Logger logger = Logger.getLogger(source.getClass());
        logger.warn(msg, cause);
    }

    /**
     *
     * @param source
     * @param msg
     */
    public static void fatal(Object source, String msg) {
        Logger logger = Logger.getLogger(source.getClass());
        logger.fatal(msg);
    }

    /**
     *
     * @param source
     * @param msg
     * @param cause
     */
    public static void fatal(Object source, String msg, Throwable cause) {
        Logger logger = Logger.getLogger(source.getClass());
        logger.fatal(msg, cause);
    }
}

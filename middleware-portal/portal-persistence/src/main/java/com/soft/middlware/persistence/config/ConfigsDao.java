package com.soft.middlware.persistence.config;

import com.soft.middlware.persistence.DaoBase;
import com.soft.middlware.persistence.DaoException;

/**
 * 
 * @author Salah Abu Msameh
 * @since 23/07/2017
 */
public class ConfigsDao extends DaoBase {

	private static final String COLUMN_KEY = "CONFIG_KEY";
	private static final String COLUMN_KEY_STRING = "CONFIG_KEY_STRING";

	/**
	 * Select configuration for the given id
	 * 
	 * @return
	 * @throws DaoException
	 */
	public ConfigEntity findConfigById(int configKey) throws DaoException {
		return findById(COLUMN_KEY, configKey, ConfigEntity.class);
	}

	/**
	 * Select configuration for the given key
	 * 
	 * @param KeyString
	 * @return
	 * @throws DaoException
	 */
	public ConfigEntity findConfigByKey(String KeyString) throws DaoException {
		return findById(COLUMN_KEY_STRING, KeyString, ConfigEntity.class);
	}
}

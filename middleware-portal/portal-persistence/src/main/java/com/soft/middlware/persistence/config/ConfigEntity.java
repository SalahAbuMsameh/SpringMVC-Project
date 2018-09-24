package com.soft.middlware.persistence.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Salah Abu Msameh
 * @since 23/07/2017
 */
@Entity
@Table(name = "CONFIGS")
public class ConfigEntity {

	private int configKey;
	private String configValue;
	private String configKeyString;

	@Id
	@Column(name = "CONFIG_KEY")
	public int getConfigKey() {
		return configKey;
	}

	public void setConfigKey(int configKey) {
		this.configKey = configKey;
	}

	@Column(name = "CONFIG_VALUE")
	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	@Column(name = "CONFIG_KEY_STRING")
	public String getConfigKeyString() {
		return configKeyString;
	}

	public void setConfigKeyString(String configKeyString) {
		this.configKeyString = configKeyString;
	}

}

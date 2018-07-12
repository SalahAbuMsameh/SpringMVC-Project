package com.warba.middlware.dao;

import org.springframework.util.StringUtils;

/**
 * 
 * @author Salah Abu Msameh
 */
public class QueryBuilder {

	/**
	 * <p>Build query with where condition for the given field</p>
	 * 
	 * e.g</br>
	 * <code>FROM typeName WHERE fieldName = :fieldName</code>
	 * 
	 * @param typeName
	 * @param fieldName
	 * @return
	 * @throws Exception 
	 */
	public static String buildQueryWhereField(String typeName, String fieldName) throws Exception {
		
		if(StringUtils.isEmpty(typeName) || StringUtils.isEmpty(fieldName)) {
			throw new Exception("Failed to build query, type name & filed name are required");
		}
		
		StringBuilder sb = new StringBuilder("FROM ");
		
		sb.append(typeName);
		sb.append(" WHERE ");
		sb.append(fieldName);
		sb.append(" = :");
		sb.append(fieldName);
		
		return sb.toString();
	}
}

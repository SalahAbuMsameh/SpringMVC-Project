package com.soft.middlware.persistence;

/**
 * Simple query string builder
 * 
 * @author Salah Abu Msameh
 * @since 4/3/2017
 */
public class QueryBuilder {
	
	public static final String COLUMN_ID = "id";

	/**
	 * build select where id condition query string</br>
	 * <b>e.g.</b> <code>FROM table WHERE idColumnName = :id</code>
	 * 
	 * @return query string
	 */
	public static String buildSelectWhereIdQuery(String idColumnName, String tableName) {
		return new StringBuilder("FROM ").append(tableName)
										 .append(" WHERE ")
										 .append(idColumnName)
										 .append(" = ")
										 .append(":").append(COLUMN_ID)
										 .toString();
	}

	/**
	 * Build Select all query string</br>
	 * <b>e.g.</b> <code>FROM table</code>
	 * 
	 * @param simpleName
	 * @return
	 */
	public static String buildSelectAllQuery(String tableName) {
		return new StringBuilder("FROM ").append(tableName)
				 						 .toString();
	}

	/**
	 * Build Select where condition query string</br>
	 * <b>e.g.</b> <code>FROM table WHERE cln1 = ? AND cln2 = ?</code>
	 * 
	 * @param columnNames
	 * @param simpleName
	 * @return
	 */
	public static String buildSelectWhere(String[] columnNames, String tableName) {
		
		StringBuilder sb = new StringBuilder("FROM ");
		
		sb.append(tableName);
		sb.append(" WHERE ");
		
		for(int i = 0; i < columnNames.length; i++) {
			
			sb.append(columnNames[i]);
			sb.append(" = ?");
			
			if(i != columnNames.length - 1) {
				sb.append(" AND ");
			}
		}
		
		return sb.toString();
	}

	/**
	 * Build Select where condition query for the given inputs</br>
	 * <b>e.g.</b> <code>"FROM table WHERE channelServiceId = ? AND channelId = ? AND userId = ?"</code>
	 * 
	 * @param levelIdCln
	 * @param levelId
	 * @param channelId
	 * @param channelServiceId
	 * @return
	 */
	public static String buildLimitLevelQuery(String levelIdCln, Object levelId, long channelId, long serviceId, String limitClassName) {
		
		StringBuilder sb = new StringBuilder("FROM ");
		
		sb.append(limitClassName);
		sb.append(" WHERE serviceId = ?");
		sb.append(" AND ");
		sb.append("channelId = ?");
		
		if(levelIdCln != null && levelId != null) {
			sb.append(" AND ");
			sb.append(levelIdCln);
			sb.append(" = ?");
		}
		
		return sb.toString();
	}
}

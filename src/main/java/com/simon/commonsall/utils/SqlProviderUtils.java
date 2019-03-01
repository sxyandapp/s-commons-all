/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */
package com.simon.commonsall.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.simon.tablegenerator.annotation.IgnoreColumn;
import com.simon.tablegenerator.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/** 
 * <pre>
 * SqlProviderUtils 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2017年11月24日
 * @version 1.0 
 */
public class SqlProviderUtils {
	
	static final ThreadLocal<QueryObject> query = new ThreadLocal<>();
	
	public static final String INSERT="_insert";
	
	public static final String INSERT_SELECTIVE="_insertSelective";
	
	public static final String SELECT="_select";
	
	public static final String UPDATE="_update";

	public static void insertPrepare(String sql) {
		query.set(new QueryObject(sql));
	}
	
	public static String _insert(Object param1) {
		return __insert(param1,true,true);
	}
	
	public static void insertSelectivePrepare(String sql) {
		query.set(new QueryObject(sql));
	}
	
	public static String insertSelective(Object param1) {
		return __insert(param1,false,false);
	}
	
	/**
	 * 当使用缓存时，不允许包含null字段，即includeNullField和useCache全为true或false
	 * @author ShiXiaoyong
	 * @date   2017年11月28日
	 * @param param1 原始对象
	 * @param includeNullField 是否包含null字段
	 * @param useCache 是否使用sql缓存
	 * @return
	 */
	static String __insert(Object param1,boolean includeNullField,boolean useCache) {
		QueryObject queryObject = query.get();
		query.remove();
		if (null==queryObject) {
			throw new NullPointerException("请先调用prepare方法");
		}
		return queryObject.sql;
	}

	public static void updatePrepare(String sql) {
		query.set(new QueryObject(sql));
	}
	
	public static String _update(Object param1) {
		return __update(param1, true, true);
	}
	
	public static void updateSelectivePrepare(String sql) {
		query.set(new QueryObject(sql));
	}
	
	public static String updateSelective(Object param1) {
		return __update(param1, false, false);
	}
	
	/**
	 * 当使用缓存时，不允许包含null字段，即includeNullField和useCache全为true或false
	 * @author ShiXiaoyong
	 * @date   2017年11月28日
	 * @param param1 原始对象
	 * @param includeNullField 是否包含null字段
	 * @param useCache 是否使用sql缓存
	 * @return
	 */
	static String __update(Object param1,boolean includeNullField,boolean useCache) {
		QueryObject queryObject = query.get();
		query.remove();
		if (null==queryObject) {
			throw new NullPointerException("请先调用prepare方法");
		}
		return queryObject.sql;
	}
	
	/**
	 * @author ShiXiaoyong
	 * @date   2017年11月27日
	 * @param sql 原始sql
	 * @param queryParameters 查询参数
	 * @param operators 运算符
	 */
	public static void selectPrepare(String sql){
		query.set(new QueryObject(sql));
	}
	
	public static void _selectPrepare(int keep){
		QueryObject queryObject = query.get();
		if (null==queryObject) {
			return;
		}
		queryObject.setKeep(keep);
	}
	
	public static String _select(Object param1){
		QueryObject queryObject = query.get();
		if (null==queryObject) {
			throw new NullPointerException("请先调用prepare方法");
		}
		if (1==queryObject.getKeep()) {
			query.remove();
		}else {
			queryObject.setKeep(queryObject.getKeep()-1);
		}
		return queryObject.sql;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	static class QueryObject{
		
		String sql;
		
		int keep = 1;

		/**
		 * Constructor
		 * @author ShiXiaoyong
		 * @date   2018年4月23日
		 * @param sql
		 */
		public QueryObject(String sql) {
			super();
			this.sql = sql;
		}
		
	}
	
	public static class Tools{
		
		static Map<String, String> SQL_CACHE_INSERT = new HashMap<>();
		
		static Map<String, String> SQL_CACHE_UPDATE = new HashMap<>();
		
		
		/**
		 * update时，会强制忽略的字段
		 * @author ShiXiaoyong
		 * @date   2017年11月28日
		 */
		static Set<String> updateBanField = new HashSet<>();
		
		static final String[] UPDATE_BY_FIELDS_DEFAULT={"id"};
		
		/**
		 * 更新时，会强制忽略的字段，通常需要将createId,createUptime添加
		 * @author ShiXiaoyong
		 * @date   2017年11月28日
		 * @param field
		 */
		public static void updateBanFields(String... field) {
			updateBanField.addAll(Arrays.asList(field));
		}
		
		public static String generateInsertSql(Class<?> clazz,String tableName,boolean useCache) {
			if (StringUtils.isEmpty(tableName)) {
				tableName = _getTableName(clazz);
			}
			if (StringUtils.isEmpty(tableName)) {
				throw new RuntimeException("无法获取tableName");
			}
			
			if (useCache) {
				//使用缓存的sql
				String cacheSql = SQL_CACHE_INSERT.get(tableName);
				if (StringUtils.isNotEmpty(cacheSql)) {
					return cacheSql;
				}
			}
			
			Set<String> fields = getFields(clazz,true);
			
			Set<String> columns = new LinkedHashSet<>();
			Set<String> values = new LinkedHashSet<>();
			for (String string : fields) {
				columns.add(StringUtils.wrap(string, '`'));
				values.add(StringUtils.join("#{",string,"}") );
			}
			
			SQL sql = new SQL();
			sql.INSERT_INTO(tableName);
			sql.INTO_COLUMNS(columns.toArray(new String[] {}));
			sql.INTO_VALUES(values.toArray(new String[] {}));
			String string = sql.toString();
			if (useCache) {
				SQL_CACHE_INSERT.put(tableName, string);
			}
			return string;
		}
		
		public static String generateUpdateSql(Class<?> clazz,String tableName,boolean useCache,String... updateByFields) {
			if (StringUtils.isEmpty(tableName)) {
				tableName = _getTableName(clazz);
			}
			if (StringUtils.isEmpty(tableName)) {
				throw new RuntimeException("无法获取tableName");
			}
			
			if (useCache) {
				String cacheSql = SQL_CACHE_UPDATE.get(tableName);
				//使用缓存的sql
				if (StringUtils.isNotEmpty(cacheSql)) {
					return cacheSql;
				}
			}
			
			Set<String> where = new LinkedHashSet<>();
			if (null==updateByFields) {
				updateByFields = UPDATE_BY_FIELDS_DEFAULT;
			}
			if (ArrayUtils.isNotEmpty(updateByFields)) {
				for (String string : updateByFields) {
					where.add(StringUtils.join(StringUtils.wrap(string, '`'),"=",StringUtils.join("#{",string,"}")));
				}
			}
			
			Set<String> fields = getFields(clazz,true);
			//移除强制忽略的字段
			fields.removeAll(updateBanField);
			fields.removeAll(Arrays.asList(updateByFields));//移除条件中的字段
			
			Set<String> set = new LinkedHashSet<>();
			for (String string : fields) {
				set.add(StringUtils.join(StringUtils.wrap(string, '`'),"=",StringUtils.join("#{",string,"}")));
			}
			
			SQL sql = new SQL();
			sql.UPDATE(tableName);
			sql.SET(set.toArray(new String[] {}));
			sql.WHERE(where.toArray(new String[] {}));
			
			String string = sql.toString();
			if (useCache) {
				SQL_CACHE_UPDATE.put(tableName, string);
			}
			return string;
		}
		
		static Set<String> getFields(Class<?> clazz,boolean includeNullField){
			Set<String> fields = new LinkedHashSet<>();
			
			Set<Field> originalFields = new LinkedHashSet<>();
			CollectionUtils.addAll(originalFields, clazz.getDeclaredFields());
			
			// 判断是否有父类，如果有拉取父类的field，这里只支持一层继承
			Class<?> clsSup = clazz.getSuperclass();
			if(clsSup!=null){
				CollectionUtils.addAll(originalFields, clsSup.getDeclaredFields());
			}
			
			if (CollectionUtils.isNotEmpty(originalFields)) {
				for (Field field : originalFields) {
					if (Modifier.isStatic(field.getModifiers()) || Modifier.isNative(field.getModifiers())) {//static,native
						continue;
					}
					if(null!=field.getAnnotation(IgnoreColumn.class)) {
						continue;
					}
					
//					if (!includeNullField) {
//						try {
//							Object invoke = field.get(param1);
//							if (null==invoke) {
//								continue;
//							}
//						} catch (IllegalAccessException | IllegalArgumentException e) {
//							e.printStackTrace();
//							continue;
//						}
//					}
					fields.add(field.getName());
				}
			}
			return fields;
		}
		
		/**
		 * @author ShiXiaoyong
		 * @date   2017年11月29日
		 * @param param1
		 * @param queryObject
		 * @return
		 */
		static String _getTableName(Class<?> clazz) {
			String tablename = null;
			if (StringUtils.isEmpty(tablename)) {
				Table annotation = clazz.getAnnotation(Table.class);
				tablename = annotation.name();
			}
			
			if (StringUtils.isEmpty(tablename)) {
				throw new NullPointerException("请传入表名,或使用@Table注解在插入对象上指定表名");
			}
			return tablename;
		}
	}
	
	
	
	
}

/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */
package com.simon.commonsall.utils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/** 
 * <pre>
 * BeanUtils 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2017年8月23日
 * @version 1.0 
 */
public class BeanUtilsEx {
	
	public static Map<String, Object> clone(Object src){
		Map<String, Object> r = new HashMap<>();
		if (null==src) {
			return r;
		}
		
		Set<Method> allDeclaredMethods = new LinkedHashSet<>();
//		Method[] allDeclaredMethods = ReflectionUtils.getAllDeclaredMethods(src.getClass());
		CollectionUtils.addAll(allDeclaredMethods, src.getClass().getMethods());
		
		Class<?> clsSup = src.getClass().getSuperclass();
		if(clsSup!=null){
			CollectionUtils.addAll(allDeclaredMethods, clsSup.getMethods());
		}
		
		if (CollectionUtils.isEmpty(allDeclaredMethods)) {
			return r;
		}
		for (Method method : allDeclaredMethods) {
			Class<?> returnType = method.getReturnType();
			int modifiers = method.getModifiers();
			if (modifiers!=1||StringUtils.equals(returnType.getName(), "void")||method.getParameterCount()>0 || !StringUtils.startsWith(method.getName(), "get")) {
				continue;
			}
			Object value = null;
			try {
				value = method.invoke(src);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String substringAfter = StringUtils.substringAfter(method.getName(), "get");
			String key = StringUtils.uncapitalize(substringAfter);
			r.put(key, value);
		}
		return r;
	}
	
}

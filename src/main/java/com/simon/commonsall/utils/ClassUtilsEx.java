/*
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved.
 */
package com.simon.commonsall.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang3.ArrayUtils;

/**
 * <pre>
 * ClassUtils
 * </pre>
 * 
 * @author ShiXiaoyong
 * @date 2017年6月15日
 * @version 1.0
 */
public class ClassUtilsEx {

	public static Class<?> getSuperClassGenricType(final Class<?> finalClazz, final Class<?> currentClazz, final int index) {
		if (index<0) {
			throw new IllegalArgumentException();
		}
		// 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
		Type genType = finalClazz.getGenericSuperclass();
		if (genType instanceof ParameterizedType) {
			// 返回表示此类型实际类型参数的 Type 对象的数组。
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
			if (ArrayUtils.getLength(params)>index && (params[index] instanceof Class)) {
				return (Class<?>) params[index];
			}
		}
		Class<?> superclass = finalClazz.getSuperclass();
		if (finalClazz.equals(currentClazz) || null==superclass || superclass.equals(currentClazz)) {
			return null;
		}
		return getSuperClassGenricType(superclass, currentClazz, index);
	}

}

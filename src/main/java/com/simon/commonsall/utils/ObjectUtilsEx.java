/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */
package com.simon.commonsall.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/** 
 * <pre>
 * ObjectUtils 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2017年6月13日
 * @version 1.0 
 */
@Slf4j(topic="ObjectUtils")
public class ObjectUtilsEx {
	
	/**
	 * 调用对象的方法
	 * @author ShiXiaoyong
	 * @date   2017年6月13日
	 * @param target
	 * @param method
	 * @param params
	 * @return
	 */
	public static Object invoke(Object bean,String methodName,String params){
		return invoke(bean, methodName, params, ',');
	}
	
	/**
	 * 调用对象的方法
	 * @author ShiXiaoyong
	 * @date   2017年11月30日
	 * @param bean
	 * @param methodName
	 * @param params
	 * @param paramSplitor 方法参数分隔符
	 * @return
	 */
	public static Object invoke(Object bean,String methodName,String params,char paramSplitor){
		Method targetMethod = null;
		if (null != bean) {
			Class<? extends Object> class1 = bean.getClass();
			while(true){
				Method[] methods = class1.getMethods();
				if (ArrayUtils.isNotEmpty(methods)) {
					for (Method item : methods) {
						QuartzScheduled annotation = item.getAnnotation(QuartzScheduled.class);
						if (null != annotation && StringUtils.equals(annotation.value(), methodName)) {
							targetMethod = item;
							break;
						}
					}
				}
				if (null!=targetMethod) {
					break;
				}
				class1 = class1.getSuperclass();
				if (class1.equals(Object.class)) {
					break;
				}
			}
		}
		if (null != targetMethod) {
			log.info("{}:{} be invoked with {}", bean, methodName, params);
			// 分离参数
			String[] split = null;
			Class<?>[] parameterTypes = targetMethod.getParameterTypes();
			Object[] targetParams = new Object[parameterTypes.length];
			
			if (1==parameterTypes.length) {
				//当目标方法只有一个参数时，将整个字符串传入
				split =new String[]{params};
			}else{
				split = StringUtils.split(params, paramSplitor);
			}
			for (int i = 0; i < parameterTypes.length; i++) {
				if (i<split.length) {
					if (parameterTypes[i] == String.class) {
						targetParams[i] = split[i];
					} else if (parameterTypes[i] == int.class || parameterTypes[i] == long.class) {
						targetParams[i] = Integer.parseInt(split[i]);
					}else if (parameterTypes[i] == boolean.class) {
						targetParams[i] = BooleanUtils.toBoolean(split[i]);
					}
				}else{
					if (parameterTypes[i] == int.class || parameterTypes[i] == long.class) {
						targetParams[i] = 0;
					}else if (parameterTypes[i] == boolean.class) {
						targetParams[i] = false;
					}
				}
			}
			try {
				return targetMethod.invoke(bean, targetParams);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			log.warn("{}:{} not exist or not be noted", bean, methodName);
		}
		return null;
	}
	
	@Target({ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public static @interface QuartzScheduled {
		
		/**
		 * 显示指定被注解方法的名称(用户混淆后识别方法名)
		 * <子属性必须被显示指定>
		 * @author Shixy
		 * @date   2016年10月25日
		 * @return
		 */
		String value() ;
	}
	
}

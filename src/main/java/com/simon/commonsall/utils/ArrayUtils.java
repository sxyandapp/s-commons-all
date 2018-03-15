/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */
package com.simon.commonsall.utils;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/** 
 * <pre>
 * ArrayUtils 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2017年8月18日
 * @version 1.0 
 */
public class ArrayUtils {
	
	public static String toString(Collection<?> list) {
		if (CollectionUtils.isEmpty(list)) {
			return StringUtils.EMPTY;
		}
		StringBuffer sb = new StringBuffer();
		
		Iterator<?> iterator = list.iterator();
		for(;;) {
			sb.append(iterator.next());
			if (!iterator.hasNext()) {
				return sb.toString();
			}
			sb.append(',');
		}
	}
	
	public static String toString(Object[] arrays) {
		if (org.apache.commons.lang3.ArrayUtils.isEmpty(arrays)) {
			return StringUtils.EMPTY;
		}
		StringBuffer sb = new StringBuffer();
		
		for (Object object : arrays) {
			sb.append(',');
			sb.append(null==object?StringUtils.EMPTY:String.valueOf(object));
		}
		if (sb.length()>1) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}
	
}

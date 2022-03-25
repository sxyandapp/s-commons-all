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
public class ArrayUtilsEx {
	
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
	
	public static <T> T getFirst(T[] tt) {
		if (org.apache.commons.lang3.ArrayUtils.isEmpty(tt)) {
			return null;
		}
		return tt[0];
	}
	
	public static <T> T getLast(T[] tt) {
		if (org.apache.commons.lang3.ArrayUtils.isEmpty(tt)) {
			return null;
		}
		return tt[tt.length-1];
	}
	
	public static byte getFirst(byte[] tt) {
		if (org.apache.commons.lang3.ArrayUtils.isEmpty(tt)) {
			return 0;
		}
		return tt[0];
	}
	
	public static byte getLast(byte[] tt) {
		if (org.apache.commons.lang3.ArrayUtils.isEmpty(tt)) {
			return 0;
		}
		return tt[tt.length-1];
	}
	
}

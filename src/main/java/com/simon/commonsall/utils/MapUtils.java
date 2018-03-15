/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */
package com.simon.commonsall.utils;

import java.util.Map;

/** 
 * <pre>
 * MapUtils 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2017年5月15日
 * @version 1.0 
 */
public class MapUtils {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void copy(Map src,Map dest){
		if (org.apache.commons.collections.MapUtils.isEmpty(src) || null==dest) {
			return;
		}
		for (Object e : src.keySet()) {
			
			dest.put(e, src.get(e));
		}
	}
	
}

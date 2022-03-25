/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */
package com.simon.commonsall.utils;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

/** 
 * <pre>
 * MapUtils 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2017年5月15日
 * @version 1.0 
 */
public class MapUtilsEx {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void copy(Map src,Map dest){
		if (org.apache.commons.collections.MapUtils.isEmpty(src) || null==dest) {
			return;
		}
		for (Object e : src.keySet()) {
			dest.put(e, src.get(e));
		}
	}
	
	public static void removeByValue(Map<?,?> src,Object v) {
		for (Iterator<?> iterator = src.values().iterator(); iterator.hasNext();) {
			Object type =iterator.next();
			if (type.equals(v)) {
				iterator.remove();
			}
		}
	}
	
    public static Object remove(Map<?, ?> src, Object k) {
        if (null == src) {
            return null;
        }
        return src.remove(k);
    }
	
    public static Map<?, ?> removeKeys(Map<?, ?> src, Object... kk) {
        if (null == src) {
            return null;
        }
        if (ArrayUtils.isEmpty(kk)) {
            return src;
        }
        for (Object k : kk) {
            src.remove(k);
        }
        return src;
    }
	
}

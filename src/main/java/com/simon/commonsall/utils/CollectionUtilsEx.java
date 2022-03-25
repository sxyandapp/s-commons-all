/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */
package com.simon.commonsall.utils;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/** 
 * <pre>
 * CollectionUtils 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2018年8月28日
 * @version 1.0 
 */
public class CollectionUtilsEx {
    
	@SuppressWarnings("unchecked")
    public static <T> T getFirst(Object object) {
	    if (null==object) {
            return null;
        }
		try {
            return (T) org.apache.commons.collections.CollectionUtils.get(object, 0);
        } catch (Exception e) {
        }
		return null;
	}
	
	@SuppressWarnings("unchecked")
    public static <T> T getLast(List<T> list) {
        if (null == list) {
            return null;
        }
        try {
            return (T) org.apache.commons.collections.CollectionUtils.get(list, list.size()-1);
        } catch (Exception e) {
        }
		return null;
	}
	
    public static void reverseArray(Object bbb) {
		if (null==bbb) {
			return ;
		}
		
		byte[] array = null;
		if (!(bbb instanceof byte[])) {
			return ;
		}
		array=(byte[]) bbb;
    	
    	
        int i = 0;
        int j = array.length - 1;
        Object tmp;

        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = (byte) tmp;
            j--;
            i++;
        }
    }
    
    public static int size(Object object) {
    	if (null==object) {
			return 0;
		}
    	return CollectionUtils.size(object);
    }
	
}

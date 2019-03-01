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
	
	public static <T> T getFirst(List<T> list) {
		if (org.apache.commons.collections.CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}
	
	public static <T> T getLast(List<T> list) {
		if (org.apache.commons.collections.CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(list.size()-1);
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

/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */
package com.simon.commonsall.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.ArrayUtils;

/** 
 * <pre>
 * StringUtils 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2017年6月22日
 * @version 1.0 
 */
public class StringUtils {

	public static String toString(byte[] buff,byte ender){
		int indexOf = ArrayUtils.indexOf(buff,ender);
		if (ArrayUtils.INDEX_NOT_FOUND ==indexOf) {
			indexOf =ArrayUtils.getLength(buff);
		}
		try {
			return new String(buff, 0, indexOf, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] toBytes(String s,int length,byte ender){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int dataLength=0;
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(s)) {
			try {
				byte[] bytes2 = s.getBytes("UTF-8");
				dataLength = bytes2.length;
				baos.write(bytes2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int f=length-dataLength;
		for (int i = 0; i < f; i++) {
			baos.write(ender);
		}
		return baos.toByteArray();
	}
	
}

/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */
package com.simon.commonsall.utils;

import org.apache.commons.codec.binary.Hex;

/** 
 * <pre>
 * HexUtils 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2017年6月8日
 * @version 1.0 
 */
public class HexUtils {
	
	public static String toString(Object array){
		if (null==array) {
			return null;
		}
		
		byte[] bb = null;
		if (!(array instanceof byte[])) {
			return null;
		}
		bb=(byte[]) array;
		
		char[] encodeHex = Hex.encodeHex(bb,false);
		
		int length = encodeHex.length/2;
		StringBuffer sb  = new StringBuffer();
		
		sb.append('{');
		for (int i = 0; i < length; i++) {
			if (0!=i) {
				sb.append(',');
			}
			sb.append('0');
			sb.append('x');
			sb.append(encodeHex[i*2]);
			sb.append(encodeHex[i*2+1]);
		}
		sb.append('}');
		return sb.toString();
	}
	
}

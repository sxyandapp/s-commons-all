/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */
package com.simon.commonsall.utils;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/** 
 * <pre>
 * StringUtils 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2017年6月22日
 * @version 1.0 
 */
public class StringUtilsEx {

	public static String toString(byte[] buff,byte ender){
		int indexOf = ArrayUtils.indexOf(buff,ender);
		if (ArrayUtils.INDEX_NOT_FOUND ==indexOf) {
			indexOf =ArrayUtils.getLength(buff);
		}
		return new String(buff, 0, indexOf, StandardCharsets.UTF_8);
	}
	
	public static byte[] toBytes(String s,int length,byte ender){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int dataLength=0;
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(s)) {
			try {
				byte[] bytes2 = s.getBytes();
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
	
	public static int skipAny(final CharSequence cs,int startPosition, final char... searchChars) {
        if (StringUtils.isEmpty(cs)|| ArrayUtils.isEmpty(searchChars)) {
            return -1;
        }
        while(true) {
            if (startPosition>= StringUtils.length(cs)) {
                break;
            }
            char charAt = cs.charAt(startPosition);
            boolean found=false;
            for (char c : searchChars) {
                if (charAt==c) {
                    found=true;
                    break;
                }
            }
            if (found) {
                startPosition++;
                continue;
            }
            break;
        }
        return startPosition;
    }
    
	public static int indexOfAny(final CharSequence cs,int startPosition, final char... searchChars) {
        if (StringUtils.isEmpty(cs)|| ArrayUtils.isEmpty(searchChars)) {
            return -1;
        }
        while(true) {
            if (startPosition>= StringUtils.length(cs)) {
                break;
            }
            char charAt = cs.charAt(startPosition);
            boolean found=false;
            for (char c : searchChars) {
                if (charAt==c) {
                    found=true;
                    break;
                }
            }
            if (found) {
                break;
            }
            startPosition++;
        }
        return startPosition;
    }
	
    public static String valueOf(Object v) {
        if (null == v) {
            return null;
        }
        return String.valueOf(v);
    }
	
}

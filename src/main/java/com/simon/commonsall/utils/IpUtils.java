/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */
package com.simon.commonsall.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

/** 
 * <pre>
 * IpUtils 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2018年8月28日
 * @version 1.0 
 */
public class IpUtils {
	
	public static String toIP6(byte[] data) {
		return toIP6(data,true);
	}
	
	public static String toIP6(byte[] data, final boolean toLowerCase) {
		if (ArrayUtils.getLength(data)<16) {
			return null;
		}
		char[] encodeHex = Hex.encodeHex(data, true);

		int length = 8;
		StringBuffer sb = new StringBuffer();

		char c1,c2,c3,c4;
		for (int i = 0; i < length; i++) {
			if (0 != i) {
				sb.append(':');
			}
			c1 = encodeHex[i * 4];
			c2 = encodeHex[i * 4 + 1];
			c3 = encodeHex[i * 4 + 2];
			c4 = encodeHex[i * 4 + 3];
			if (c1!='0') {
				sb.append(c1);
				sb.append(c2);
				sb.append(c3);
				sb.append(c4);
			}else if(c2!='0') {
				sb.append(c2);
				sb.append(c3);
				sb.append(c4);
			}else if(c3!='0') {
				sb.append(c3);
				sb.append(c4);
			}else if(c4!='0') {
				sb.append(c4);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @author ShiXiaoyong
	 * @date   2018年10月13日
	 * @param ip6
	 * @param shortable true:短ipv6,false:unimplement
	 * @return
	 */
	public static String toIP6(String ip6,boolean shortable) {
		String[] split = StringUtils.split(ip6,':');
		StringBuilder sb = new StringBuilder();
		if (shortable) {
			for (String string : split) {
				sb.append(':');
				sb.append(StringUtils.stripStart(string, "0"));
			}
			if (sb.length()>0) {
				sb.deleteCharAt(0);
			}
		}else {
			throw new NotImplementedException("");
		}
		return sb.toString();
	}
	
	public static String toIP4(byte[] data) {
		if (ArrayUtils.getLength(data)<4) {
			return null;
		}
		return StringUtils.joinWith(",", data[0]& 0xff, data[1]& 0xff, data[2]& 0xff, data[3]& 0xff);
	}
}

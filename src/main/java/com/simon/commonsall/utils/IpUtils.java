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
		char[] encodeHex = Hex.encodeHex(data);

		int length = 8;
		StringBuffer sb = new StringBuffer();

		char c1,c2,c3,c4;
		for (int i = 0; i < length; i++) {
			if (0 != i) {
				sb.append(':');
			}
			int point=i*4;
			sb.append(encodeHex[point]);
			sb.append(encodeHex[point+1]);
			sb.append(encodeHex[point+2]);
			sb.append(encodeHex[point+3]);
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @author ShiXiaoyong
	 * @date   2018年10月13日
	 * @param ip6
	 * @param shortable true:短ipv6,false:长ipv6
	 * @return
	 */
	public static String toIP6(String ip6,boolean shortable) {
		String[] split = StringUtils.splitPreserveAllTokens(ip6,':');
		StringBuilder sb = new StringBuilder();
		if (shortable) {
			// for (String string : split) {
			// 	sb.append(':');
			// 	sb.append(StringUtils.stripStart(string, "0"));
			// }
			// if (sb.length()>0) {
			// 	sb.deleteCharAt(0);
			// }
			throw new NotImplementedException("");
		}else {
			for (String string : split) {
				if (StringUtils.isEmpty(string)) {
					int abcentPart = 8 - StringUtils.countMatches(ip6, ':');
					for (int i = 0; i < abcentPart; i++) {
						sb.append(":0000");
					}
				} else {
					sb.append(':');
					sb.append(StringUtils.leftPad(string, 4, '0'));
				}
			}
			if (sb.length()>0) {
				sb.deleteCharAt(0);
			}
		}
		return sb.toString();
	}
	
	public static String toIP4(byte[] data) {
		if (ArrayUtils.getLength(data)<4) {
			return null;
		}
		return StringUtils.joinWith(".", data[0]& 0xff, data[1]& 0xff, data[2]& 0xff, data[3]& 0xff);
	}
	
	public static byte[] fromIP4(String ipString) {
	    if (StringUtils.isEmpty(ipString)) {
            return null;
        }
	    String[] split = StringUtils.split(ipString,'.');
	    if (ArrayUtils.getLength(split)<4) {
            return null;
        }
	    byte[] ip = new byte[4];
	    for (int i = 0; i < 4; i++) {
            ip[i] = (byte) Integer.parseUnsignedInt(split[i]);
        }
	    return ip;
	}
}

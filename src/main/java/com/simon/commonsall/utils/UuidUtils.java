package com.simon.commonsall.utils;

import java.util.UUID;

/** 
 * 描述：UuidUtils 
 *  
 * @author Shixy 
 * @date   2015年6月29日
 * @since  1.0 
 */
public class UuidUtils {

	public static String uuid() {
		return UUID.randomUUID().toString();
	}
	
	public static String guid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}

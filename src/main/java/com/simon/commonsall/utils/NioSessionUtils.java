/*
 * Copyright (c) 2014, ShiXiaoyong. All rights reserved.
 */
package com.simon.commonsall.utils;

import java.net.InetSocketAddress;

import org.apache.mina.core.session.IoSession;

/**
 * 描述：NioSessionUtil
 * 
 * @author Shixy
 * @date 2015年8月20日
 * @since 1.0
 */
public class NioSessionUtils {

	private static final String _SESSION_ID = "_SESSION_ID";

	public static String getSessionId(IoSession session) {
		if (!session.containsAttribute(_SESSION_ID)) {
			session.setAttribute(_SESSION_ID, UuidUtils.guid());
		}
		return (String) session.getAttribute(_SESSION_ID);
	}

	public static int getSessionRemotePort(IoSession session) {
		if (null==session) {
			return 0;
		}
		InetSocketAddress socketAddress = (InetSocketAddress) session.getRemoteAddress();
		return socketAddress.getPort();
	}
	
	public static String getSessionRemoteIp(IoSession session){
		if (null==session) {
			return null;
		}
		InetSocketAddress socketAddress = (InetSocketAddress) session.getRemoteAddress();
		return socketAddress.getAddress().getHostAddress();
	}

	public static String getSessionLocalInfo(IoSession session) {
		if (null==session) {
			return null;
		}
		InetSocketAddress socketAddress = (InetSocketAddress) session.getLocalAddress();
		return socketAddress.getAddress().getHostAddress() + ":" + socketAddress.getPort();
	}

	public static String getSessionRemoteInfo(IoSession session) {
		if (null==session) {
			return null;
		}
		InetSocketAddress socketAddress = (InetSocketAddress) session.getRemoteAddress();
		return socketAddress.getAddress().getHostAddress() + ":" + socketAddress.getPort();
	}

}

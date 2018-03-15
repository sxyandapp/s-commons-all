/*
 * Copyright (c) 2014, ShiXiaoyong. All rights reserved.
 */
package com.simon.commonsall.utils;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * <pre>
 * HttpUtils
 * </pre>
 * 
 * @author Shixy
 * @date 2016年8月4日
 * @since 1.0
 */
public class HttpUtils {

	public static HttpURLConnection postAsyn(String url, Map<String, String> headers) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

			// add request header
			conn.setRequestMethod("POST");
			iniHeader(conn, headers);

			conn.setDoInput(true);
			conn.setDoOutput(true);
			return conn;
			// get response
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static InputStream post(String url, byte[] data, Map<String, String> headers) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

			// add request header
			conn.setRequestMethod("POST");
			iniHeader(conn, headers);

			conn.setDoInput(true);
			if (ArrayUtils.isNotEmpty(data)) {
				conn.setDoOutput(true);
				// 写入数据
				DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				wr.write(data);
				wr.flush();
				wr.close();
			}

			// get response
			return conn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static InputStream get(String url, Map<String, String> headers) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

			// add request header
			conn.setRequestMethod("GET");
			iniHeader(conn, headers);
			conn.setDoOutput(true);

			// get response
			return conn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void iniHeader(HttpURLConnection conn, Map<String, String> headers) {
		if (MapUtils.isNotEmpty(headers)) {
			for (String key : headers.keySet()) {
				conn.setRequestProperty(key, headers.get(key));
			}
		}
	}

}

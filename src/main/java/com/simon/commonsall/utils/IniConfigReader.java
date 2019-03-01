/*
 * Copyright (c) 2014, ShiXiaoyong. All rights reserved.
 */
package com.simon.commonsall.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * IniConfigReader
 * </pre>
 * 
 * @author Shixy
 * @date 2015年12月29日
 * @since 1.0
 */
public class IniConfigReader {

	private static final String ENCODING = "utf-8";

	private static final String[] lineFilter = new String[] { "#" };

	/**
	 * 读取ini文件
	 * 
	 * @author Shixy
	 * @date 2015年12月29日
	 * @param filePath
	 * @return
	 */
	public static Map<String, Map<String, String>> readIni(String filePath) {
		try {
			Map<String, Map<String, String>> data = new LinkedHashMap<String, Map<String, String>>();
			List<String> lines = com.simon.commonsall.utils.FileUtilsEx.readLines(filePath, ENCODING, lineFilter);
			String currentSection = null;
			Map<String, String> e = null;
			for (String string : lines) {
				if (StringUtils.isEmpty(string)) {
					continue;
				}
				string = StringUtils.trimToEmpty(string);
				if (string.startsWith("[") && string.endsWith("]")) {
					currentSection = string.substring(1, string.length() - 1);
					e = new LinkedHashMap<String, String>();
					data.put(currentSection, e);
					continue;
				}
				if (string.contains("=")) {
					String[] userPrivileges = string.split("=");
					e.put(StringUtils.trim(userPrivileges[0]), userPrivileges.length > 1 ? StringUtils.trim(userPrivileges[1]) : StringUtils.EMPTY);
					continue;
				}
			}

			return data;
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * 写入ini文件
	 * 
	 * @author Shixy
	 * @date 2015年12月29日
	 * @param filePath
	 * @param data
	 */
	public static void writeIni(String filePath, Map<String, Map<String, String>> data) {
		if (null==data) {
			data = new HashMap<String, Map<String,String>>();
		}
		List<String> dataLines = new ArrayList<String>();
		for (String section : data.keySet()) {
			dataLines.add("[" + section + "]");
			Map<String, String> item = data.get(section);
			if (MapUtils.isNotEmpty(item)) {
				for (String key : item.keySet()) {
					dataLines.add(key + "=" + item.get(key));
				}
			}
		}
		try {
			FileUtils.writeLines(new File(filePath), ENCODING, dataLines);
		} catch (IOException e) {
		}
	}

	/**
	 * 读取keyvalue文件
	 * @author Shixy
	 * @date   2015年12月29日
	 * @param filePath
	 * @return
	 */
	public static Map<String, String> readKeyValue(String filePath) {
		try {
			Map<String, String> data = new LinkedHashMap<String, String>();
			List<String> lines = com.simon.commonsall.utils.FileUtilsEx.readLines(filePath, ENCODING, lineFilter);
			for (String string : lines) {
				if (StringUtils.isEmpty(string)) {
					continue;
				}
				String[] userPrivileges = string.split(":");
				data.put(userPrivileges[0], userPrivileges.length > 1 ? userPrivileges[1] : null);
			}

			return data;
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * 写入keyvalue文件
	 * @author Shixy
	 * @date   2015年12月29日
	 * @param filePath
	 * @param data
	 */
	public static void writeKeyValue(String filePath, Map<String, String> data) {
		if (null==data) {
			data = new HashMap<String, String>();
		}
		List<String> dataLines = new ArrayList<String>();
		for (String user : data.keySet()) {
			dataLines.add(user +":" + data.get(user));
		}
		try {
			FileUtils.writeLines(new File(filePath), ENCODING, dataLines);
		} catch (IOException e) {
		}
	}

}

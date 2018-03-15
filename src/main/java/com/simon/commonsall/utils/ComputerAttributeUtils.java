/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */
package com.simon.commonsall.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.sun.jna.Platform;

/** 
 * <pre>
 * ComputerAttributeUtils 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2017年12月19日
 * @version 1.0 
 */
public class ComputerAttributeUtils {
	
	public static String harddiskInfo() {
		if (Platform.isWindows()) {
			return null;
		}
		InputStream inputStream = null;
		try {
			Process exec = Runtime.getRuntime().exec("fdisk -l");
			inputStream = exec.getInputStream();
			List<String> readLines = IOUtils.readLines(inputStream,"UTF-8");
			if (CollectionUtils.isNotEmpty(readLines)) {
				for (String string : readLines) {
					if (StringUtils.startsWith(string, "Disk identifier:")) {
						return StringUtils.trim(StringUtils.substringAfter(string, "Disk identifier:"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return null;
	}
	
	/**
	 * centos 7+
	 * @author ShiXiaoyong
	 * @date   2017年12月20日
	 * @return
	 */
	public static List<String> mac() {
		if (Platform.isWindows()) {
			return mac4Window();
		}else if (Platform.isLinux()) {
			return mac4Linux();
		}
		return null;
	}
	
	static List<String> mac4Window(){
		InputStream inputStream = null;
		try {
			Process exec = Runtime.getRuntime().exec("ipconfig /all");
			inputStream = exec.getInputStream();
			
			List<String> readLines = IOUtils.readLines(inputStream,"GB2312");
			if (CollectionUtils.isNotEmpty(readLines) && readLines.size() > 3) {
				List<String> r = new ArrayList<>();
				for (int i = 0; i < readLines.size(); i++) {
					String string = readLines.get(i);
					if (!StringUtils.contains(string, "物理地址")) {
						continue;
					}
					boolean findone=true;
					for (int j = i; j >0; j--) {
						String string2 = readLines.get(j);
						if (StringUtils.isBlank(string2)) {
							break;
						}
						if (StringUtils.contains(string2, "媒体状态") && StringUtils.contains(string2, "媒体已断开") ) {
							findone = false;
							break;
						}
					}
					if (!findone) {
						continue;
					}
					
					findone=false;
					for (int j = i; j < readLines.size(); j++) {
						String string2 = readLines.get(j);
						if (StringUtils.isBlank(string2)) {
							break;
						}
						
						if (StringUtils.contains(string2, "IPv4 地址") ) {
							findone = true;
							break;
						}
					}
					if (!findone) {
						continue;
					}
					
					r.add(StringUtils.trim(StringUtils.substringAfter(string, ":")));
				}
				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return null;
	}
	
	static List<String> mac4Linux(){
		InputStream inputStream = null;
		try {
			Process exec = Runtime.getRuntime().exec("ifconfig");
			inputStream = exec.getInputStream();
			
			List<String> readLines = IOUtils.readLines(inputStream,"UTF-8");
			if (CollectionUtils.isNotEmpty(readLines) && readLines.size() > 3) {
				List<List<String>> nn = new ArrayList<>();
				List<String> n = new ArrayList<>();
				nn.add(n);
				for (String string : readLines) {
					if (StringUtils.isBlank(string)) {
						n = new ArrayList<>();
						nn.add(n);
						continue;
					}
					n.add(string);
				}
				
				for (Iterator<List<String>> iterator = nn.iterator(); iterator.hasNext();) {
					List<String> list = iterator.next();
					if (CollectionUtils.isEmpty(list)) {
						iterator.remove();
						continue;
					}
					if (!StringUtils.contains(list.get(0), "flags=4163")) {
						iterator.remove();
					}
				}
				
				List<String> r = new ArrayList<>();
				for (List<String> list : nn) {
					for (String string : list) {
						if (StringUtils.contains(string, "ether")) {
							boolean findOne = false;
							String[] split = StringUtils.split(string);
							for (int i = 0; i < split.length; i++) {
								if (StringUtils.equals(split[i], "ether")) {
									r.add(split[i+1]);
									findOne = true;
									break;
								}
							}
							if (findOne) {
								break;
							}
						}
					}
				}
				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return null;
	}
	
}

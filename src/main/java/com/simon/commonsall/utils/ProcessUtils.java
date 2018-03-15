/*
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved.
 */
package com.simon.commonsall.utils;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.sun.jna.Platform;

/**
 * <pre>
 * ProcessUtils
 * </pre>
 * 
 * @author ShiXiaoyong
 * @date 2017年3月23日
 * @version 1.0
 */
public class ProcessUtils {

	/**
	 * 判断某进程是否存在(linux only)
	 * 
	 * @author ShiXiaoyong
	 * @date 2017年3月23日
	 * @param pid
	 * @return
	 */
	public static boolean isPIDExists(String pid) {
		if (Platform.isLinux()) {
			if (StringUtils.isEmpty(pid)) {
				return false;
			}
			InputStream inputStream = null;
			try {
				Process exec = Runtime.getRuntime().exec(new String[] { "sh", "-c", "ps -e|grep \\ " + pid + "\\ " });
				inputStream = exec.getInputStream();
				List<String> readLines = IOUtils.readLines(inputStream);
				if (CollectionUtils.isNotEmpty(readLines)) {
					return true;
				}
				return false;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
		return true;
	}

	public static boolean isJPIDExists(String pid) {
		if (Platform.isLinux()) {
			if (StringUtils.isEmpty(pid)) {
				return false;
			}
			InputStream inputStream = null;
			try {
				Process exec = Runtime.getRuntime().exec("jps");
				inputStream = exec.getInputStream();
				List<String> readLines = IOUtils.readLines(inputStream);
				if (CollectionUtils.isNotEmpty(readLines)) {
					pid += " ";
					for (String string : readLines) {
						if (StringUtils.startsWith(string, pid)) {
							return true;
						}
					}
				}
				return false;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
		return true;
	}

	public static boolean killProcess(String pid) {
		if (Platform.isLinux()) {
			if (StringUtils.isEmpty(pid)) {
				return false;
			}
			try {
				Runtime.getRuntime().exec("kill -9 " + pid);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public static boolean killProcessByName(String pname) {
		if (Platform.isLinux()) {
			if (StringUtils.isEmpty(pname)) {
				return false;
			}
			try {
				Runtime.getRuntime().exec("pkill " + pname);//正常杀
				Thread.sleep(300);//必须有间隔
				Runtime.getRuntime().exec("pkill -9 " + pname);//强制杀
				Thread.sleep(100);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 使用子线程启动新的进程
	 * 
	 * @author ShiXiaoyong
	 * @date 2017年3月23日
	 * @param cmd
	 */
	public static void exec(String cmd) {
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}

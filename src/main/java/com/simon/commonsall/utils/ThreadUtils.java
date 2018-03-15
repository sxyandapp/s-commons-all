/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */
package com.simon.commonsall.utils;

import java.awt.EventQueue;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * ThreadUtils
 * </pre>
 * 
 * @author Shixy
 * @date 2016年5月26日
 * @since 1.0
 */
public class ThreadUtils {

	/**
	 * @author ShiXiaoyong
	 * @date   2017年5月11日
	 * @param r
	 */
	public static void run(Runnable r) {
		new Thread(r).start();
	}

	/**
	 * @author ShiXiaoyong
	 * @date   2017年5月11日
	 * @param millis
	 */
	public static void sleep(long millis) {
		try {
			TimeUnit.MILLISECONDS.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void sleepSeconds(long seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void sleepMinutes(long minutes) {
		try {
			TimeUnit.MINUTES.sleep(minutes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * GUI progrom only
	 * @author ShiXiaoyong
	 * @date   2017年5月11日
	 * @param r
	 */
	public static void runOnUiThread(Runnable r) {
		EventQueue.invokeLater(r);
	}

}
